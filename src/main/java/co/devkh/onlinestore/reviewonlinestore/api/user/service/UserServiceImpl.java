package co.devkh.onlinestore.reviewonlinestore.api.user.service;

import co.devkh.onlinestore.reviewonlinestore.api.user.data.Role;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.RoleRepository;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.User;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.UserRepository;
import co.devkh.onlinestore.reviewonlinestore.api.user.mapper.UserMapper;
import co.devkh.onlinestore.reviewonlinestore.api.user.projection.UserInfo;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.NewUserDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UpdateUserDto;
import co.devkh.onlinestore.reviewonlinestore.api.user.web.user_dto.UserDto;
import co.devkh.onlinestore.reviewonlinestore.base.request.BaseListingRQ;
import co.devkh.onlinestore.reviewonlinestore.base.response.StructureRS;
import co.devkh.onlinestore.reviewonlinestore.base.service.BaseService;
import co.devkh.onlinestore.reviewonlinestore.exception.CredentialException;
import co.devkh.onlinestore.reviewonlinestore.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends BaseService implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto me(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        log.info("Name : {}",jwt.getId());
        log.info("Subject : {}",jwt.getSubject());

        User user = userRepository.findByUsernameAndIsDeletedFalseAndIsVerifiedTrue(jwt.getId()).orElseThrow(() ->
                new UsernameNotFoundException("User is not found."));
        return userMapper.toUserDto(user);
    }


    @Transactional
    @Override
    public void createNewUser(NewUserDto newUserDto) {

        // Check for existing username and email efficiently
        if (userRepository.existsByUsernameOrEmailAndIsDeletedFalse(
                newUserDto.username(), newUserDto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Username or email already exists.");
        }

        User newUser = userMapper.fromNewUserDto(newUserDto);
        newUser.setUuid(UUID.randomUUID().toString());
        newUser.setIsVerified(false);
        newUser.setIsDeleted(false);
        newUser.setPassword(passwordEncoder.encode(newUserDto.password()));

       boolean isNotFound = newUserDto.roleIds().stream().anyMatch(roleId->!roleRepository.existsById(roleId));

        if (isNotFound){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Role ID does not exist..!");
        }
// Tacitly, we assume that the role IDs are valid.
        Set<Role> roles = newUserDto.roleIds().stream()
                .map(roleId->Role.builder().id(roleId).build())
                .collect(Collectors.toSet());

        newUser.setRoles(roles);
        userRepository.save(newUser);
    }

    @Transactional
    @Override
    public void updateByUuid(String uuid, UpdateUserDto updateUserDto) {
        // Check email if exists
        if (userRepository.existsByEmailAndIsDeletedFalse(updateUserDto.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists..!");
        }

        User foundUser = userRepository.selectUserByUuid(uuid).orElseThrow(() ->
         new ResponseStatusException(HttpStatus.NOT_FOUND,"User is not Found..!"));

        userMapper.fromUpdateUserDto(foundUser,updateUserDto);
        userRepository.save(foundUser);
    }

    @Override
    public UserDto findUserByUuid(String uuid) {
       User foundUser = userRepository.selectUserByUuidAndIsDeleted(uuid,false).orElseThrow(() ->
              new ResponseStatusException(HttpStatus.NOT_FOUND,
                      String.format("User UUID = %s doesn't exist in db!",uuid)));
        return userMapper.toUserDto(foundUser);
    }


    @Transactional
    @Override
    public void deleteByUuid(String uuid) {
        User foundUser = userRepository.selectUserByUuid(uuid).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User UUID = %s doesn't exist in db!",uuid)));

        userRepository.delete(foundUser);
    }

    @Transactional
    @Override
    public void updateIsDeletedByUuid(String uuid, Boolean isDeleted) {

        Boolean isFound = userRepository.checkUserByUuid(uuid);

        if (isFound){
            userRepository.updateIsDeletedStatusByUuid(uuid,isDeleted);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User UUID = %s doesn't exist in db!",uuid));
    }

    @Override
    public StructureRS findAllUsers(BaseListingRQ request) {
        Page<UserInfo> userInfoPage = userRepository.findByUuidStartsWithAndUsernameContainsAndEmailContainsAndRoles_NameContains(
                request.getQuery(),request.getPageable(request.getSort(),request.getOrder()));
        return response(userInfoPage.getContent(),userInfoPage);
    }

    @Override
    public Optional<CustomUserDetails> findUserByUsername(String username) {
        User user = userRepository.findByUsernameAndIsDeletedFalseAndIsVerifiedTrue(username)
                .orElseThrow(() ->
                        new CredentialException(HttpStatus.NOT_FOUND,
                                String.format("Username = %s has not been found..!",username)));

        CustomUserDetails userDetails = CustomUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthorities(user.getRoles()))
                .enabled(!user.getIsDeleted())
                .build();
        log.info("Security user = {}",userDetails.getUsername());
        log.info("Email user = {}",user.getEmail());
        log.info("Authorities = {}",userDetails.getAuthorities());
        return Optional.of(userDetails);
    }
    private Set<SimpleGrantedAuthority> getAuthorities(Set<Role> roles){

        Set<SimpleGrantedAuthority> authorities1 = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .flatMap(this::toStream)
                .collect(Collectors.toSet());

        authorities.addAll(authorities1);
        return authorities;
    }

    private Stream<SimpleGrantedAuthority> toStream(Role role){
        return role.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()));
    }
}

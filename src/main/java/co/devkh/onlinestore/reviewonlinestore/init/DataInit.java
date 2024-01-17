package co.devkh.onlinestore.reviewonlinestore.init;

import co.devkh.onlinestore.reviewonlinestore.api.brand.Brand;
import co.devkh.onlinestore.reviewonlinestore.api.brand.BrandRepository;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.CategoryRepository;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.Supplier;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.SupplierRepository;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.Authority;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.AuthorityRepository;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.Role;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final SupplierRepository supplierRepository;
    @PostConstruct
    public void init(){

        // Staff-Authorities-Management
        Authority readProduct = Authority.builder().name("product:read").build();
        Authority writeProduct = Authority.builder().name("product:write").build();
        Authority deleteProduct = Authority.builder().name("product:delete").build();
        Authority updateProduct = Authority.builder().name("product:update").build();
        Authority patchProduct = Authority.builder().name("product:patch").build();
        Set<Authority> productAuthorities = Set.of(readProduct,writeProduct,deleteProduct,updateProduct,patchProduct);
        authorityRepository.saveAll(productAuthorities);

        // User-Authorities-Management
        Authority readUser = Authority.builder().name("user:read").build();
        Authority writeUser = Authority.builder().name("user:write").build();
        Authority deleteUser = Authority.builder().name("user:delete").build();
        Authority updateUser = Authority.builder().name("user:update").build();
        Authority patchUser = Authority.builder().name("user:patch").build();
        Authority userProfile = Authority.builder().name("user:profile").build();
        Set<Authority> userAuthorities = Set.of(readUser,writeUser,deleteUser,updateUser,patchUser,userProfile);
        authorityRepository.saveAll(userAuthorities);

        // Role-Authorities-Management


        // Combine Authorities (Staff + User)
        Set<Authority> fullAuthorities = new HashSet<>(){{
            addAll(productAuthorities);
            addAll(userAuthorities);
        }};


        // Role Management : Admin,Staff,Customer

        Role adminRole = Role.builder()
                .name("ADMIN")
                .authorities(fullAuthorities)
                .build();

        Role staffRole = Role.builder()
                .name("STAFF")
                .authorities(new HashSet<>(){{
                    addAll(productAuthorities);
                    add(userProfile);
                }})
                .build();

        Role customerRole = Role.builder()
                .name("CUSTOMER")
                .authorities(Set.of(readProduct,userProfile))
                .build();

        roleRepository.saveAll(List.of(adminRole,staffRole,customerRole));

        // Brands Management : Init Data
        Brand asus = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("ASUS").build();
        Brand asusZenBook = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("ASUS ZenBook").build();
        Brand tufGaming = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("TUF GAMING").build();
        Brand rog = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("REPUBLIC OF GAMERS").build();
        Set<Brand> asusBrand = Set.of(asus,asusZenBook,tufGaming,rog);
//        brandRepository.saveAll(asusBrand);

        Brand msi = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("MSI").build();
        Set<Brand> msiBrand = Set.of(msi);
//        brandRepository.saveAll(msiBrand);

        Brand lenovo = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("LENOVO").build();
        Set<Brand> lenovoBrand = Set.of(lenovo);
//        brandRepository.saveAll(lenovoBrand);

        Brand alien_ware = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("ALIEN_WARE").build();
        Brand predator = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("PREDATOR").build();
        Set<Brand> dellBrand = Set.of(alien_ware,predator);
//        brandRepository.saveAll(dellBrand);

        Brand intel = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("INTEL").build();
        Set<Brand> intelBrand = Set.of(intel);
//        brandRepository.saveAll(intelBrand);

        Brand amd = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("AMD").build();
        Set<Brand> amdBrand = Set.of(amd);
//        brandRepository.saveAll(amdBrand);

        Brand kingston = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("KINGSTON").build();
        Set<Brand> kingstonBrand = Set.of(kingston);
//        brandRepository.saveAll(kingstonBrand);

        Brand xpg = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("XPG").build();
        Set<Brand> xpgBrand = Set.of(xpg);
//        brandRepository.saveAll(xpgBrand);

        Brand corsair = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("CORSAIR").build();
        Set<Brand> corsairBrand = Set.of(corsair);
//        brandRepository.saveAll(corsairBrand);

        Brand adata = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("ADATA").build();
        Set<Brand> adataBrand = Set.of(adata);
//        brandRepository.saveAll(adataBrand);

        Brand gigabyte = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("GIGABYTE").build();
        Set<Brand> gigabyteBrand = Set.of(gigabyte);
//        brandRepository.saveAll(gigabyteBrand);

        Brand aorus = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("AORUS").build();
        Set<Brand> aorusBrand = Set.of(aorus);
//        brandRepository.saveAll(aorusBrand);

        Brand samsung = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("SAMSUNG").build();
        Set<Brand> samsungBrand = Set.of(samsung);
//        brandRepository.saveAll(samsungBrand);

        Brand razer = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("RAZER").build();
        Set<Brand> razerBrand = Set.of(razer);
//        brandRepository.saveAll(razerBrand);

        Brand logitech = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("LOGITECH").build();
        Set<Brand> logitechBrand = Set.of(logitech);
//        brandRepository.saveAll(logitechBrand);

        Brand scorpion = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("SCORPION").build();
        Set<Brand> scorpionBrand = Set.of(scorpion);
//        brandRepository.saveAll(scorpionBrand);

        Brand dx_racer = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("DX-RACER").build();
        Set<Brand> dx_racerBrand = Set.of(dx_racer);
//        brandRepository.saveAll(dx_racerBrand);

        Brand tp_link = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("TP-LINK").build();
        Set<Brand> tp_linkBrand = Set.of(tp_link);
//        brandRepository.saveAll(tp_linkBrand);

        Brand truPower = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("TRU-POWER").build();
        Set<Brand> truPowerBrand = Set.of(truPower);
//        brandRepository.saveAll(truPowerBrand);

        Brand apc = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("APC").build();
        Set<Brand> apcBrand = Set.of(apc);
//        brandRepository.saveAll(apcBrand);

        Brand orico = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("ORICO").build();
        Set<Brand> oricoBrand = Set.of(orico);
//        brandRepository.saveAll(oricoBrand);

        Brand targus = Brand.builder().brandUuid(UUID.randomUUID().toString()).brandName("TARGUS").build();
        Set<Brand> targusBrand = Set.of(targus);
//        brandRepository.saveAll(targusBrand);

    // save all brand objects of Set
        brandRepository.saveAll(new ArrayList<>(){{
            addAll(asusBrand);
            addAll(msiBrand);
            addAll(lenovoBrand);
            addAll(dellBrand);
            addAll(intelBrand);
            addAll(amdBrand);
            addAll(kingstonBrand);
            addAll(xpgBrand);
            addAll(corsairBrand);
            addAll(adataBrand);
            addAll(gigabyteBrand);
            addAll(aorusBrand);
            addAll(samsungBrand);
            addAll(razerBrand);
            addAll(logitechBrand);
            addAll(scorpionBrand);
            addAll(dx_racerBrand);
            addAll(tp_linkBrand);
            addAll(truPowerBrand);
            addAll(apcBrand);
            addAll(oricoBrand);
            addAll(targusBrand);
        }});

        // Category <-> Brand Management : Laptop, PC Hardware , Peripherals, Accessories
        Category laptop = Category.builder()
                .name("LAPTOP")
                .description("laptop")
                .brands(new ArrayList<>(){{
                    addAll(asusBrand);
                    addAll(msiBrand);
                    addAll(lenovoBrand);
                    addAll(dellBrand);
                }})
                .build();
//        categoryRepository.saveAll(List.of(laptop));

        Category pc_hardware = Category.builder()
                .name("PC-HARDWARE")
                .description("pc hardware")
                .brands(new ArrayList<>(){{
                    add(asus);
                    addAll(msiBrand);
                    addAll(intelBrand);
                    addAll(amdBrand);
                    addAll(kingstonBrand);
                    addAll(xpgBrand);
                    addAll(corsairBrand);
                    addAll(adataBrand);
                    addAll(gigabyteBrand);
                    addAll(aorusBrand);
                    addAll(samsungBrand);
                }})
                .build();
//        categoryRepository.saveAll(List.of(pc_hardware));

        Category peripherals = Category.builder()
                .name("PERIPHERALS")
                .description("peripherals")
                .brands(new ArrayList<>(){{
                        addAll(razerBrand);
                        addAll(corsairBrand);
                        addAll(dx_racerBrand);
                        addAll(logitechBrand);
                        add(rog);
                        addAll(scorpionBrand);
                    }})
                .build();
//        categoryRepository.saveAll(List.of(peripherals));

        Category accessories = Category.builder()
                .name("ACCESSORIES")
                .description("accessories")
                .brands(new ArrayList<>(){{
                    addAll(tp_linkBrand);
                    addAll(samsungBrand);
                    addAll(adataBrand);
                    addAll(truPowerBrand);
                    addAll(apcBrand);
                    addAll(oricoBrand);
                    addAll(targusBrand);
                    add(asus);
                }})
                .build();
//        categoryRepository.saveAll(List.of(accessories));
        categoryRepository.saveAll(List.of(laptop,pc_hardware,peripherals,accessories));


        // Suppliers Management : Init Data
        // Supplier <-> Category Management : Laptop, PC Hardware, Peripherals, Accessories
        Supplier asusMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("ASUSTeK Computer Inc.")
                .contactName("Charlotte Cooper")
                .contactEmail("email@asus.com")
                .address("No. 15, Li-De Road, Beitou District, Taipei 112, Taiwan")
                .city("Taipei City")
                .country("Taiwan")
                .phone("+886-2-2894-3447")
                .categories(new ArrayList<>(){{
                    add(laptop);
                    add(pc_hardware);
                    add(peripherals);
                    add(accessories);
                }})
                .build();
        Set<Supplier> asusSupplier = Set.of(asusMetaData);

        Supplier msiMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Micro-Star International Co., Ltd.")
                .contactName("Shelley Burke")
                .contactEmail("acs@msi.com")
                .address("No. 69, Lide St., Zhonghe Dist., New Taipei City 235, Taiwan")
                .city("Taipei City")
                .country("Taiwan")
                .phone("+886-2-3234-5599")
                .categories(new ArrayList<>(){{
                    add(laptop);
                    add(pc_hardware);
                    add(peripherals);
                }})
                .build();
        Set<Supplier> msiSupplier = Set.of(msiMetaData);

        Supplier lenovoMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Lenovo Group Limited")
                .contactName("Regina Murphy")
                .contactEmail("Lencare@lenovo.com")
                .address("Quarry Bay, Hong Kong")
                .city("Quarry Bay")
                .country("Hong Kong")
                .phone("1-855-253-6686")
                .categories(new ArrayList<>(){{
                    add(laptop);
                }})
                .build();
        Set<Supplier> lenovoSupplier = Set.of(lenovoMetaData);

        Supplier dellMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Dell Technologies Inc.")
                .contactName("Yoshi Nagase")
                .contactEmail("Board_of_Directors@Dell.com")
                .address("Round Rock, Texas, United States")
                .city("Texas")
                .country("United States")
                .phone("1-877-275-3355")
                .categories(new ArrayList<>(){{
                    add(laptop);
                }})
                .build();
        Set<Supplier> dellSupplier = Set.of(dellMetaData);

        Supplier acerMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Acer Inc")
                .contactName("Antonio del Valle Saavedra ")
                .contactEmail("ail.easycare@acer.com")
                .address("New Taipei City, Taiwan")
                .city("Taipei City")
                .country("Taiwan")
                .phone("1800116677")
                .categories(new ArrayList<>(){{
                    add(laptop);
                }})
                .build();
        Set<Supplier> acerSupplier = Set.of(acerMetaData);

        Supplier intelMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Intel Corporation")
                .contactName("Mayumi Ohno")
                .contactEmail("John.Smith@intel.com")
                .address("Santa Clara, California")
                .city("California")
                .country("United States")
                .phone("(+1) 916-377-7000")
                .categories(new ArrayList<>(){{
                    add(pc_hardware);
                }})
                .build();
        Set<Supplier> intelSupplier = Set.of(intelMetaData);

        Supplier amdMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Advanced Micro Devices, Inc.")
                .contactName("Ian Devling")
                .address("Santa Clara, California, United States")
                .city("California")
                .country("United States")
                .phone("877-284-1566 (Toll Free)")
                .categories(new ArrayList<>(){{
                    add(pc_hardware);
                }})
                .build();
        Set<Supplier> amdSupplier = Set.of(amdMetaData);

        Supplier kingstonMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Kingston Technology Corporation")
                .contactName("Peter Wilson")
                .address("17600 Newhope Street Fountain Valley, CA 92708 USA")
                .city("California")
                .country("United States")
                .phone("+1 (714) 435-2600")
                .categories(new ArrayList<>(){{
                    add(pc_hardware);
                }})
                .build();
        Set<Supplier> kingstonSupplier = Set.of(kingstonMetaData);

        Supplier adataMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("ADATA Technology Co., Ltd.")
                .contactName("Lars Peterson")
                .contactEmail("adata@adata.com")
                .address("China. Beijing")
                .city("Beijing")
                .country("China")
                .phone("+86-10-5128-6922")
                .categories(new ArrayList<>(){{
                    add(pc_hardware);
                    add(accessories);
                }})
                .build();
        Set<Supplier> adataSupplier = Set.of(adataMetaData);

        Supplier corsairMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Corsair Gaming, Inc.")
                .contactName("Carlos Diaz")
                .address("115 N. McCarthy Blvd. Milpitas, CA 95035")
                .city("California")
                .country("United States")
                .phone("+1 844-348-8999")
                .categories(new ArrayList<>(){{
                    add(pc_hardware);
                    add(peripherals);
                }})
                .build();
        Set<Supplier> corsairSupplier = Set.of(corsairMetaData);

        Supplier gigabyteMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Gigabyte Technology")
                .contactName("Petra Winkler")
                .contactEmail("www.gigabyte.com")
                .address("No.6, Baoqiang Rd., Xindian Dist., New Taipei City 231, Taiwan")
                .city("Taipei City")
                .country("Taiwan")
                .phone("+886-2-8912-4000")
                .categories(new ArrayList<>(){{
                    add(pc_hardware);
                }})
                .build();
        Set<Supplier> gigabyteSupplier = Set.of(gigabyteMetaData);

        Supplier samsungMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Samsung Electronics Co., Ltd.")
                .contactName("Martin Bein")
                .contactEmail("www.samsung.com")
                .address("129, Samsung-ro, Yeongtong-gu,. Suwon-si, Gyeonggi-do, Korea")
                .city("Yeongtong-gu,. Suwon-si, Gyeonggi-do")
                .country("Korea")
                .phone("0800-555-7267")
                .categories(new ArrayList<>(){{
                    add(pc_hardware);
                    add(accessories);
                }})
                .build();
        Set<Supplier> samsungSupplier = Set.of(samsungMetaData);

        Supplier razerMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Razer Inc.")
                .contactName("Sven Petersen")
                .address("9 Pasteur Ste 100 Irvine, CA 92618 United States")
                .city("California")
                .country("United States")
                .phone("888-872-5233")
                .categories(new ArrayList<>(){{
                    add(peripherals);
                }})
                .build();
        Set<Supplier> razerSupplier = Set.of(razerMetaData);

        Supplier logitechMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Logitech International S.A.")
                .contactName("Elio Rossi")
                .address("3930 North First Street San Jose, CA 95134")
                .city("California")
                .country("United States")
                .phone("+1 510-795-8500")
                .categories(new ArrayList<>(){{
                    add(peripherals);
                }})
                .build();
        Set<Supplier> logitechSupplier = Set.of(logitechMetaData);

        Supplier marvoMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("MarvoTech Co.Ltd")
                .contactName("Beate Vileid")
                .contactEmail("www.marvo-tech.com")
                .address("6th Floor, Building A, DongFangYaYua")
                .city("Shenzhen")
                .country("China")
                .phone("(+86) 755 2970 9393")
                .categories(new ArrayList<>(){{
                    add(peripherals);
                }})
                .build();
        Set<Supplier> marvoSupplier = Set.of(marvoMetaData);

        Supplier dx_racerMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("DXRACER Co.Ltd")
                .contactName("Cheryl Saylor")
                .contactEmail("www.dxracer.com")
                .address("9177 E. Michigan 36 Whitmore Lake, MI 48189")
                .city("Michigan")
                .country("United States")
                .phone("（855）627-5724(Toll-free)")
                .categories(new ArrayList<>(){{
                    add(peripherals);
                }})
                .build();
        Set<Supplier> dx_racerSupplier = Set.of(dx_racerMetaData);

        Supplier tpLinkMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("TP-Link Technologies Company Ltd.")
                .contactName("Wendy Mackenzie")
                .contactEmail("sales.usa@tp-link.com")
                .address("Shenzhen Shi, 8 Wushitou Rd, Nanshan Qu, China")
                .city("Shenzhen")
                .country("China")
                .phone("+1 909 527 6804")
                .categories(new ArrayList<>(){{
                    add(accessories);
                }})
                .build();
        Set<Supplier> tpLinkSupplier = Set.of(tpLinkMetaData);

        Supplier truPowerMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Tru Power, Inc.")
                .contactName("Jean-Guy Lauzon")
                .contactEmail("www.trupowercorp.com")
                .address("Washington, DC 20004, US")
                .city("Washington")
                .country("United States")
                .phone("+855-78 888 045")
                .categories(new ArrayList<>(){{
                    add(accessories);
                }})
                .build();
        Set<Supplier> truPowerSupplier = Set.of(truPowerMetaData);

        Supplier apcMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("American Power Conversion Corporation")
                .contactName("Marie Delamare")
                .contactEmail("contact.nyc@apc.fr")
                .address("West Kingston, South Kingstown, Rhode Island, United States")
                .city("West Kingston")
                .country("United States")
                .phone("+877-272-2722")
                .categories(new ArrayList<>(){{
                    add(accessories);
                }})
                .build();
        Set<Supplier> apcSupplier = Set.of(apcMetaData);

        Supplier oricoMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Shenzhen ORICO Technologies Co., Ltd.")
                .contactName("Eliane Noz")
                .contactEmail("supports@orico.com.cn")
                .address("19/F, Block 14A, Zhonghaixin Science & Technology Park, Longgang District, Shenzhen, China.")
                .city("Shenzhen")
                .country("China")
                .phone("+867 318 896 5801")
                .categories(new ArrayList<>(){{
                    add(accessories);
                }})
                .build();
        Set<Supplier> oricoSupplier = Set.of(oricoMetaData);

        Supplier targusMetaData = Supplier.builder()
                .uuid(UUID.randomUUID().toString())
                .companyName("Targus International LLC")
                .contactName("Chantal Goulet")
                .contactEmail("ex.jdoe@targus.com")
                .address("1211 N Miller St.Anaheim, CA 92806")
                .city("California")
                .country("United States")
                .phone("(714) 765-5555")
                .categories(new ArrayList<>(){{
                    add(accessories);
                }})
                .build();
        Set<Supplier> targusSupplier = Set.of(targusMetaData);

        supplierRepository.saveAll(new ArrayList<>(){{
            addAll(asusSupplier);
            addAll(msiSupplier);
            addAll(lenovoSupplier);
            addAll(dellSupplier);
            addAll(acerSupplier);
            addAll(intelSupplier);
            addAll(amdSupplier);
            addAll(kingstonSupplier);
            addAll(adataSupplier);
            addAll(corsairSupplier);
            addAll(gigabyteSupplier);
            addAll(samsungSupplier);
            addAll(razerSupplier);
            addAll(logitechSupplier);
            addAll(marvoSupplier);
            addAll(dx_racerSupplier);
            addAll(tpLinkSupplier);
            addAll(truPowerSupplier);
            addAll(apcSupplier);
            addAll(oricoSupplier);
            addAll(targusSupplier);
        }});
    }
}
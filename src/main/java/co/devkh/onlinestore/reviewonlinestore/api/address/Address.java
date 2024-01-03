package co.devkh.onlinestore.reviewonlinestore.api.address;

import co.devkh.onlinestore.reviewonlinestore.api.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50,nullable = false)
    private String code;
    @Column(length = 200,nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;
    @Column(length = 200,nullable = false)
    private String type;
    @Column(name = "parent_code",nullable = false,length = 60)
    private String parentCode;
    @Column(columnDefinition = "TEXT")
    private String reference;
    @Column(name = "official_note",columnDefinition = "TEXT")
    private String officialNote;
    @Column(name = "note_by_checker")
    private String noteByChecker;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

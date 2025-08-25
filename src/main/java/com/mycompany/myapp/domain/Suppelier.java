package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "suppelier")
public class Suppelier extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String suppelierId;      // PK (String)

    private String name;
    private String address;
    private String phone;

    private Long userId;               // FK: long (không map quan hệ)

    // Giữ “form” như bạn dùng: getId()/setId() thao tác với suppelierId
    public String getId() { return suppelierId; }
    public void setId(String id) { this.suppelierId = id; }

    public String getSuppelierId() { return suppelierId; }
    public void setSuppelierId(String suppelierId) { this.suppelierId = suppelierId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Suppelier() {}

    public Suppelier(
        String suppelierId, String name, String address, String phone,
        LocalDateTime updatedDate, LocalDateTime updatedBy,
        LocalDateTime createdDate, LocalDateTime createdBy,
        Boolean isDeleted, Long userId
    ) {
        this.suppelierId = suppelierId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.userId = userId;
    }
}

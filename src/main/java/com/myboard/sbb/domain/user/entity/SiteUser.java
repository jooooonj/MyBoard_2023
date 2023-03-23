package com.myboard.sbb.domain.user.entity;

import com.myboard.sbb.shared.base.baseentity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteUser extends BaseEntity {

    @Column(unique = true)
    @NotNull
    private String userId;
    @NotNull
    private String password;
    @NotNull
    private String username;
    @Column(unique = true)
    private String email;

}

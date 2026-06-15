package com.shortLink.ShortLink.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Builder.Default
    @Column(nullable = false ,updatable = false)
    private Long clickCount = 0L;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(unique = true)
    private String shortCode;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now ();
    }
}

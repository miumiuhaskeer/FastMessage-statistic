package com.miumiuhaskeer.fastmessage.statistic.model.entity

import javax.persistence.*

@Entity
@Table(name = "fm_role")
class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen")
    @SequenceGenerator(name = "id_gen", sequenceName = "fm_role_id_seq", allocationSize = 1)
    var id: Long? = null

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    var name: ERole? = null
}
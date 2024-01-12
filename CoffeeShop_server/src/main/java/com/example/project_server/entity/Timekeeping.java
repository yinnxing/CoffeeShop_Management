package com.example.project_server.entity;

import com.example.project_server.serializable.TimekeepingId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "timekeeping")
public class Timekeeping {
    @EmbeddedId
    private TimekeepingId id;
    @ManyToOne
    @MapsId("staffId")
    @JoinColumn(name = "staff_id")
    @JsonIgnore
    private Staff staff;
}

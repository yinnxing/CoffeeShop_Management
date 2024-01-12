package com.example.project_server.serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class TimekeepingId implements Serializable {
    @Column(name = "staff_id")
    String staffId;
    @Column(name="work_date")
    String workDate;
}

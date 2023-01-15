package com.revature.models.idclasses;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.IdClass;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerId
        implements Serializable {
    private Integer followed;
    private Integer following;

}

package com.web.ddajait.model.dto.ChallengePart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Day {
    int day;
    Chapter chapter;
    boolean complete;
    String memo;
}

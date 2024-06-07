package org.daeskk.datastructures;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.daeskk.enums.TokenType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private TokenType type;


}

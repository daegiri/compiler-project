package org.daeskk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.daeskk.datastructures.Token;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lexer {
    private List<Token> tokens = new ArrayList<>();


}

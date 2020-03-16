package org.bayesianfreaks.nlp.tokenizer;

import org.bayesianfreaks.nlp.parserc.Source;
import org.bayesianfreaks.nlp.parserc.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Tokenizer {
    public Optional<Token> read(final StateMachine.State state, final Source source);

    default public Optional<List<Token>> tokenize(final StateMachine m, final Source source) {
        final List<Token> tokens = new ArrayList<>();
        while (true) {
            final Optional<Token> result = this.read(m.getState(), source);
            result.ifPresent(tokens::add);
            result.ifPresent(m::receive);
            if (result.isEmpty()) {
                break;
            }
        }
        if (source.isEof()) {
            return Optional.of(tokens);
        } else {
            return Optional.empty();
        }
    }

}
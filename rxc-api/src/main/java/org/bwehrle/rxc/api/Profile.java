package org.bwehrle.rxc.api;

import com.sun.istack.internal.NotNull;
import lombok.Data;

@Data
public class Profile {
    @NotNull
    public final String name;
    @NotNull
    public final String selectionRule;
}

package io.github.thatrobin.docky.mixin;

import io.github.apace100.calio.data.SerializableDataType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SerializableDataType.class, remap = false)
public interface SerializableDataTypeAccessor<T> {

    @Accessor("dataClass")
    Class<T> getDataClass();
}

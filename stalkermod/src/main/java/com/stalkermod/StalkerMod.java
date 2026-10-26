package com.stalkermod;

import com.stalkermod.entity.LurkerEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class StalkerMod implements ModInitializer {
    public static final String MOD_ID = "stalkermod";

    public static final EntityType<LurkerEntity> LURKER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MOD_ID, "lurker"),
            EntityType.Builder.create(LurkerEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 2.8F)
                    .build("lurker")
    );

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(LURKER, LurkerEntity.createAttributes());
    }
}

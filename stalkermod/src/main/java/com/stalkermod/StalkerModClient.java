package com.stalkermod;

import com.stalkermod.entity.LurkerModel;
import com.stalkermod.entity.LurkerRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class StalkerModClient implements ClientModInitializer {
    public static final EntityModelLayer LURKER_LAYER = new EntityModelLayer(
            Identifier.of(StalkerMod.MOD_ID, "lurker"), "main"
    );

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(StalkerMod.LURKER, LurkerRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(LURKER_LAYER, LurkerModel::getTexturedModelData);
    }
}

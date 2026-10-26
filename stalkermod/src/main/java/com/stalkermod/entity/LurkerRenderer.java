package com.stalkermod.entity;

import com.stalkermod.StalkerMod;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class LurkerRenderer extends MobEntityRenderer<LurkerEntity, LurkerModel> {
    private static final Identifier TEXTURE = Identifier.of(StalkerMod.MOD_ID, "textures/entity/lurker.png");

    public LurkerRenderer(EntityRendererFactory.Context context) {
        super(context, new LurkerModel(context.getPart(EntityModelLayers.PLAYER)), 0.5F);
    }

    @Override
    public Identifier getTexture(LurkerEntity entity) {
        return TEXTURE;
    }
}

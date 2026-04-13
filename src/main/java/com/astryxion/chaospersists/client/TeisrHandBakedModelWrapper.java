package com.astryxion.chaospersists.client;

import java.util.List;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

/**
 * Marks an item as using a TEISR and records the hand {@link ItemCameraTransforms.TransformType}
 * so rendering can use 3D in-hand and the flat JSON model elsewhere.
 */
public class TeisrHandBakedModelWrapper implements IBakedModel {

    private final IBakedModel inner;

    public TeisrHandBakedModelWrapper(IBakedModel inner) {
        this.inner = inner;
    }

    public IBakedModel getInner() {
        return inner;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return inner.getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return inner.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return inner.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return inner.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return inner.getItemCameraTransforms();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return inner.getOverrides();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        switch (cameraTransformType) {
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND: {
                TeisrHandTransformHolder.set(cameraTransformType);
                Pair<? extends IBakedModel, Matrix4f> innerPerspective = inner.handlePerspective(cameraTransformType);
                Matrix4f mat = innerPerspective != null ? innerPerspective.getRight() : null;
                if (mat == null) {
                    mat = new Matrix4f();
                    mat.setIdentity();
                }
                return Pair.of(this, mat);
            }
            default:
                return inner.handlePerspective(cameraTransformType);
        }
    }
}

package com.disgraded.gdxmachine.framework.systems

import com.badlogic.ashley.core.Family
import com.badlogic.gdx.math.Vector2
import com.disgraded.gdxmachine.framework.components.MaskComponent
import com.disgraded.gdxmachine.framework.components.SpriteComponent
import com.disgraded.gdxmachine.framework.components.TextComponent
import com.disgraded.gdxmachine.framework.components.Transform2DComponent
import com.disgraded.gdxmachine.framework.core.engine.Entity
import com.disgraded.gdxmachine.framework.core.engine.System
import com.disgraded.gdxmachine.framework.drawables.Drawable2D
import com.disgraded.gdxmachine.framework.renderers.Renderer2D
import com.disgraded.gdxmachine.framework.utils.Transform2D

class Render2DSystem: System(
        Family.all(
                Transform2DComponent::class.java
        ).one(
                SpriteComponent::class.java,
                MaskComponent::class.java,
                TextComponent::class.java
        ).get()
) {

    private var tempVec2 = Vector2()

    override fun initialize(entities: ArrayList<Entity>) {
        val layer = core.graphics.createLayer()
        layer.setRenderer(Renderer2D())
    }

    override fun update(deltaTime: Float, entities: ArrayList<Entity>) {
        for (entity in entities) {
            processEntity(entity)
        }
    }

    private fun processEntity(entity: Entity) {
        val transform = entity.get(Transform2DComponent::class)!!
        val sprite = entity.get(SpriteComponent::class)
        val mask = entity.get(MaskComponent::class)
        val text = entity.get(TextComponent::class)

        if (sprite != null) {
            sprite.absolute.inherit(sprite)
            applyTransform(sprite.absolute, sprite, transform)
            draw(sprite.absolute, sprite.layer)
        }

        if (mask != null) {
            mask.absolute.inherit(mask)
            applyTransform(mask.absolute, mask, transform)
            draw(mask.absolute, mask.layer)
        }

        if (text !== null) {
            text.absolute.inherit(text)
            applyTransform(text.absolute, text, transform)
            draw(text.absolute, text.layer)
        }
    }

    private fun draw(drawable2D: Drawable2D, layer: String?) {
        if (layer != null) {
            core.graphics.getLayer(layer).draw(drawable2D)
        } else {
            core.graphics.getLayer().draw(drawable2D)
        }
    }

    private fun applyTransform(drawable: Drawable2D, reference: Drawable2D, transform2D: Transform2D) {
        tempVec2.set(reference.x + transform2D.x, reference.y + transform2D.y)
        tempVec2.sub(transform2D.x, transform2D.y).rotate(transform2D.angle).add(transform2D.x, transform2D.y)
        drawable.setPosition(tempVec2.x, tempVec2.y)
        drawable.angle = transform2D.angle + reference.angle
        drawable.setScale(reference.scaleX * transform2D.scaleX, reference.scaleY * transform2D.scaleY)
        drawable.z += transform2D.z
    }

    override fun destroy(entities: ArrayList<Entity>) {
        println("destroy")
    }
}
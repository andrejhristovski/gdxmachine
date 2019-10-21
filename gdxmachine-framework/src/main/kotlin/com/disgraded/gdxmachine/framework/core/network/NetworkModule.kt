package com.disgraded.gdxmachine.framework.core.network

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.disgraded.gdxmachine.framework.core.Module

class NetworkModule : Module {

    val api: Net = Gdx.net

    override fun load() {}

    override fun unload() {}
}
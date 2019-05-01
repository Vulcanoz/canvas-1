/*******************************************************************************
 * Copyright 2019 grondag
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package grondag.canvas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import grondag.canvas.apiimpl.RendererImpl;
import grondag.frex.api.RendererAccess;
import net.fabricmc.api.ModInitializer;

//TODO: configurable occlusion hook
//TODO: configurable render region
//TODO: configurable disable chunk matrix
//TODO: configurable cutout single pass, separate pass may give early cull in solid
//TODO: more dynamic vertex formats
//TODO: configurable vanilla model vertex adjustment
//TODO: configurable compact vertex formats - GPU side  white, face, unlit, pixel-aligned, etc.
//TODO: configurable compressed vertex formats - CPU side (maybe wait for Brocade Mesh)
//TODO: capture & log more GL capability info - allow disable, enabled by default
//TODO: configurable super smooth lighting
//TODO: per chunk occlusion mesh - for sky shadow mask
//TODO: per chunk depth mesh - addendum to occlusion mesh to render for depth pass - includes translucent cutout

public class Canvas implements ModInitializer {
    @Override
    public void onInitialize() {
        Configurator.init();
        RendererAccess.INSTANCE.registerRenderer(RendererImpl.INSTANCE);
    }

    public static final String MODID = "canvas";
    
    public static final Logger LOG = LogManager.getLogger("Canvas");
}

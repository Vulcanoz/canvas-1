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

package grondag.canvas.chunk.occlusion;

import grondag.fermion.functions.PrimitiveFunctions.ObjToIntFunction;
import net.minecraft.client.render.chunk.ChunkOcclusionGraphBuilder;
import net.minecraft.util.math.BlockPos;

/**
 * Used to get static access to private static block pos hash function.
 */
public abstract class ChunkOcclusionBuilderAccessHelper {
    public static final ObjToIntFunction<BlockPos> PACK_FUNCTION;
    static
    {
        ChunkOcclusionGraphBuilderExt visData = (ChunkOcclusionGraphBuilderExt) new ChunkOcclusionGraphBuilder();
        PACK_FUNCTION = visData.canvas_pack();
    }
    
    public interface ChunkOcclusionGraphBuilderExt {
        /** Actually static - use to get and hold lambda */
        ObjToIntFunction<BlockPos> canvas_pack();
        
        void canvas_clear();
    }
}


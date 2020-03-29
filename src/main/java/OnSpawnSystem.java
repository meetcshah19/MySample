/*
 * Copyright 2020 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.terasology.assets.management.AssetManager;
import org.terasology.entitySystem.entity.EntityBuilder;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.prefab.Prefab;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.inventory.InventoryComponent;
import org.terasology.logic.inventory.InventoryManager;
import org.terasology.logic.location.LocationComponent;
import org.terasology.logic.players.event.OnPlayerSpawnedEvent;
import org.terasology.math.Side;
import org.terasology.math.geom.Vector3f;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.In;
import org.terasology.structureTemplates.events.SpawnStructureEvent;
import org.terasology.structureTemplates.util.BlockRegionTransform;
import org.terasology.world.WorldProvider;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockUri;
import org.terasology.world.block.items.OnBlockItemPlaced;
import org.terasology.world.chunks.event.OnChunkLoaded;

@RegisterSystem(RegisterMode.AUTHORITY)
public class OnSpawnSystem extends BaseComponentSystem {
    @In
    private EntityManager entityManager;
    @In
    private InventoryManager inventoryManager;
    @In
    private AssetManager assetManager;
    @In
    private WorldProvider worldProvider;
    @ReceiveEvent
    public void onPlayerSpawn(OnPlayerSpawnedEvent event, EntityRef player, InventoryComponent inventory) {

        Prefab prefab = assetManager.getAsset("MySample:hut", Prefab.class).orElse(null);
        EntityBuilder entityBuilder = entityManager.newBuilder(prefab);
        EntityRef item = entityBuilder.build();
        Prefab prefab2 = assetManager.getAsset("MySample:well", Prefab.class).orElse(null);
        EntityBuilder entityBuilder2 = entityManager.newBuilder(prefab2);
        EntityRef item2 = entityBuilder2.build();
        Vector3i vector3i = new Vector3i(5,10,5);
        Vector3i vector3i2 = new Vector3i(15,9,15);
        Block block=new Block();
        block.setUri(new BlockUri("CoreBlocks:Dirt"));
        worldProvider.setBlock(vector3i,block);
        BlockRegionTransform b = BlockRegionTransform.createRotationThenMovement(Side.FRONT, Side.FRONT,vector3i);
        item.send(new SpawnStructureEvent(b));
        worldProvider.setBlock(vector3i2,block);
        BlockRegionTransform b2 = BlockRegionTransform.createRotationThenMovement(Side.FRONT, Side.FRONT,vector3i2);
        item2.send(new SpawnStructureEvent(b2));
    }
}
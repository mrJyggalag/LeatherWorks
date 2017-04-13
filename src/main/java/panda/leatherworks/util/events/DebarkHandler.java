package panda.leatherworks.util.events;

import panda.leatherworks.blocks.BlockDebarkedLog;
import panda.leatherworks.util.registry.BlockList;
import panda.leatherworks.util.registry.ItemList;
import panda.leatherworks.util.registry.MasterRegistrar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DebarkHandler {
	@SubscribeEvent(priority=net.minecraftforge.fml.common.eventhandler.EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
		EntityPlayer player = event.getEntityPlayer();
		World world = player.getEntityWorld();

		IBlockState state = world.getBlockState(event.getPos());


		if ( !player.isSneaking() || !(state.getBlock() instanceof BlockLog)|| state.getBlock() instanceof BlockDebarkedLog) {
			
			return;
		}
		//System.out.println("doot");
		
		//if(world.getTotalWorldTime() % 4 ==0){
			world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 2F, 1.0F);
		//}
		
		if(!world.isRemote){
		ItemStack heldStack = player.getHeldItemMainhand();
		if (heldStack != null)
		{
			if(heldStack.getItem() == Items.FLINT){
				if (world.rand.nextInt(20) == 0)
				{
					IBlockState newState = findCorrectState(state);
					
					world.setBlockState(event.getPos(), newState, 3);
					 ItemStack stackOut = findCorrectStack(state);
					 if (stackOut != null) {
						 EntityItem entityitem = new EntityItem(world, player.posX, player.posY, player.posZ, stackOut);
						 world.spawnEntityInWorld(entityitem);
 
						 if (!(player instanceof FakePlayer)) {
						 entityitem.onCollideWithPlayer(player);
						 }
					}
				}
			}
		}
		}
	}

	private IBlockState findCorrectState(IBlockState state) {
		Block block = state.getBlock();
		int meta;
		
		EnumAxis axis = state.getValue(BlockLog.LOG_AXIS);

		
		if( block== Blocks.LOG){
			meta = block.getMetaFromState(state);
			
			switch(meta%4){
			
			case 0:
				return BlockList.DEBARKED_LOG_OAK.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
			case 1:
				return BlockList.DEBARKED_LOG_SPRUCE.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
			case 2:
				return BlockList.DEBARKED_LOG_BIRCH.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
			case 3:
				return BlockList.DEBARKED_LOG_JUNGLE.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
			
			}
		}else 
		if( block== Blocks.LOG2){
			meta = block.getMetaFromState(state);
			switch(meta%4){
			case 0:
				return BlockList.DEBARKED_LOG_ACACIA.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
			case 1:
				return BlockList.DEBARKED_LOG_DARKOAK.getDefaultState().withProperty(BlockDebarkedLog.LOG_AXIS,axis );
			}
		}
		return null;
	}
	
	private ItemStack findCorrectStack(IBlockState state) {
		Block block = state.getBlock();
		int meta;
		if( block== Blocks.LOG){
			meta = block.getMetaFromState(state);

				return new ItemStack(ItemList.BARK,1,meta%4);

		}else 
		if( block== Blocks.LOG2){
			meta = block.getMetaFromState(state);
			System.out.println(meta%4);
			switch(meta%4){
			case 0:
				return new ItemStack(ItemList.BARK,1,meta%4);
			case 1:
				return new ItemStack(ItemList.BARK,1,meta%4);
			}
		}
		return null;
	}
}

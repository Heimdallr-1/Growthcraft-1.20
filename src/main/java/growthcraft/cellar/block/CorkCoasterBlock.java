package growthcraft.cellar.block;

import growthcraft.cellar.block.entity.CorkCoasterBlockEntity;
import growthcraft.cellar.init.GrowthcraftCellarBlockEntities;
import growthcraft.core.utils.BlockPropertiesUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class CorkCoasterBlock extends BaseEntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public CorkCoasterBlock() {
		this(getInitProperties());
	}
	
	protected CorkCoasterBlock(Properties pProperties) {
		super(pProperties);
	}

	private static Properties getInitProperties() {
		Properties properties = BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS);
        properties.strength(1.5F);
        properties.noOcclusion();
        properties.isValidSpawn(BlockPropertiesUtils::never);
        properties.isRedstoneConductor(BlockPropertiesUtils::never);
        properties.isViewBlocking(BlockPropertiesUtils::never);
		return properties;
	}
	
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return GrowthcraftCellarBlockEntities.CORK_COASTER_BLOCK_ENTITY.get().create(pPos, pState);
	}

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
	
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,  BlockHitResult pHit) {
		int slot = 0;
		CorkCoasterBlockEntity blockEntity = (CorkCoasterBlockEntity) pLevel.getBlockEntity(pPos);
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
         	ItemStack slotStack = blockEntity.getItem(slot);
            if ((!itemInHand.isEmpty() && blockEntity.canPlaceItem(slot, itemInHand)) || !slotStack.isEmpty() && (itemInHand.isEmpty() || blockEntity.canPlaceItem(slot, itemInHand))) {
            	blockEntity.setItem(slot, itemInHand);
            	pPlayer.setItemInHand(pHand, slotStack);

//                pState = pState.setValue(SWORD, !blockEntity.isEmpty());
            	pLevel.setBlock(pPos, pState, UPDATE_ALL);
                return InteractionResult.SUCCESS; 
            }
    	return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}

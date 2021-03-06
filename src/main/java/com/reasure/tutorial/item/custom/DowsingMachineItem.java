package com.reasure.tutorial.item.custom;

import com.reasure.tutorial.TutorialMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class DowsingMachineItem extends Item {
    public DowsingMachineItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        TutorialMod.getLOGGER().info("client: " + world.isClientSide());

        if (world.isClientSide) {
            PlayerEntity player = context.getPlayer();
            // 클릭한 위치
            BlockPos clickedPos = context.getClickedPos();

            boolean foundBlock = false; // 가치 있는 블럭을 발견했는가

            for (int i = 1; i <= clickedPos.getY(); i++) {
                // world.getBlockState(blockPos) : 해당 좌표의 블럭 상태를 얻어옴
                // blockPos.below(정수형) : 몊 칸 아래 좌표를 얻어옴
                BlockState belowBlock = world.getBlockState(clickedPos.below(i));

                if (isValuableBlock(belowBlock)) {
                    foundBlock = true;
                    player.displayClientMessage(new StringTextComponent("Found Block"), false);
                    break;
                }
            }

            if (!foundBlock) {
                player.displayClientMessage(new StringTextComponent("No Found Block. LOL"), false);
            }
        }

        context.getItemInHand().hurtAndBreak(1, context.getPlayer(),
                player -> player.broadcastBreakEvent(context.getHand()));

        return ActionResultType.sidedSuccess(world.isClientSide);
    }

    // 해당 함수는 커스텀 태그를 다루면 더 업그레이드 될 예정
    private boolean isValuableBlock(BlockState block) {
        // 원하는건 더 추가 가능
        return block.is(Blocks.CHEST) || block.is(BlockTags.GOLD_ORES) || block.is(Tags.Blocks.ORES_DIAMOND);
    }
}

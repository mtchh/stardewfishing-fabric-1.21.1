package com.kltyton.stardewfishingFabric.common.networking;

import com.kltyton.stardewfishingFabric.common.FishBehavior;
import net.minecraft.network.FriendlyByteBuf;

// 定义从服务器到客户端（S2C）的开始迷你游戏的数据包
public class S2CStartMinigamePacket {
    private final FishBehavior behavior;

    public S2CStartMinigamePacket(FishBehavior behavior) {
        this.behavior = behavior;
    }

    // 将数据包内容编码到PacketByteBuf中，以便通过网络发送
    public void encode(FriendlyByteBuf buf) {
        behavior.writeToBuffer(buf);
    }
}

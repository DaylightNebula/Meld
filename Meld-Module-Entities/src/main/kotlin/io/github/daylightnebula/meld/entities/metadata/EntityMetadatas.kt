package io.github.daylightnebula.meld.entities.metadata

import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata
import io.github.daylightnebula.meld.server.utils.Pose
import org.json.JSONObject

fun entityMetadata(
    flags: Byte = 0x00,
    airTicks: Int = 300,
    customName: JSONObject? = null,
    isCustomNameVisible: Boolean = false,
    isSilent: Boolean = false,
    hasNoGravity: Boolean = false,
    pose: Pose = Pose.STANDING,
    frozenTick: Int = 0
) = metadata(
    metaByte(0, flags),
    metaVarInt(1, airTicks),
    metaOptChat(2, customName),
    metaBoolean(3, isCustomNameVisible),
    metaBoolean(4, isSilent),
    metaBoolean(5, hasNoGravity),
    metaPose(6, pose),
    metaVarInt(7, frozenTick)
)
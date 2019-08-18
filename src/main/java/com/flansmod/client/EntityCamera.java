package com.flansmod.client;

import com.flansmod.common.FlansMod;
import com.flansmod.common.driveables.EntityDriveable;
import com.flansmod.common.guns.EntityBullet;
import com.flansmod.common.vector.Vector3f;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.world.World;

import com.flansmod.common.driveables.EntityDriveable;
import com.flansmod.common.vector.Vector3f;

public class EntityCamera extends EntityLivingBase
{
	public EntityDriveable driveable;
	
	public EntityCamera(World world)
	{
		super(world);
		setSize(0F, 0F);
	}
	
	public EntityCamera(World world, EntityDriveable d)
	{
		this(world);
		driveable = d;
		setPosition(d.posX, d.posY, d.posZ);
	}
	
	@Override
	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		
		Vector3f cameraPosition = new Vector3f();
		cameraPosition = driveable.axes.findLocalVectorGlobally(cameraPosition);
		
		// Lerp it
		double dX = driveable.posX + cameraPosition.x - posX;
		double dY = driveable.posY + cameraPosition.y - posY;
		double dZ = driveable.posZ + cameraPosition.z - posZ;
		
		float lerpAmount = 0.1F;
		
		setPosition(posX + dX * lerpAmount, posY + dY * lerpAmount, posZ + dZ * lerpAmount);
		
		if(FlansMod.proxy.mouseControlEnabled())
		{
			rotationYaw = driveable.axes.getYaw() - 90F;
			rotationPitch = driveable.axes.getPitch();
		}
		else
		{
			Entity player = FlansMod.proxy.getThePlayer();
			if(player != null)
			{
				rotationYaw		= player.rotationYaw;
				rotationPitch	= player.rotationPitch;
			}
		}
		
		
		while(rotationYaw - prevRotationYaw >= 180F)
		{
			rotationYaw -= 360F;
		}
		while(rotationYaw - prevRotationYaw < -180F)
		{
			rotationYaw += 360F;
		}
	}
	
	@Override
	public Iterable<ItemStack> getArmorInventoryList()
	{
		return null;
	}
	
	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn)
	{
		return ItemStack.EMPTY.copy();
	}
	
	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack)
	{
	
	}
	
	@Override
	public EnumHandSide getPrimaryHand()
	{
		return EnumHandSide.RIGHT;
	}
	
}

package com.simibubi.create.modules.contraptions.components.crank;

import com.simibubi.create.AllTileEntities;
import com.simibubi.create.modules.contraptions.base.GeneratingKineticTileEntity;

public class HandCrankTileEntity extends GeneratingKineticTileEntity {

	public int inUse;
	public boolean backwards;
	public float independentAngle;
	public float chasingVelocity;
	
	public HandCrankTileEntity() {
		super(AllTileEntities.HAND_CRANK.type);
	}
	
	public void turn(boolean back) {
		boolean update = false;
		
		if (getGeneratedSpeed() == 0 || back != backwards)
			update = true;
		
		inUse = 10;
		this.backwards = back;
		if (update && !world.isRemote)
			updateGeneratedRotation();
	}

	@Override
	public float getGeneratedSpeed() {
		return inUse == 0 ? 0 : backwards ? -128 : 128;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		float actualSpeed = getSpeed();
		chasingVelocity += (actualSpeed - chasingVelocity) * .25f;
		independentAngle += chasingVelocity;
		
		if (inUse > 0) {
			inUse--;
			
			if (inUse == 0 && !world.isRemote) 
				updateGeneratedRotation();
		}
	}

}
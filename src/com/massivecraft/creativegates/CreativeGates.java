package com.massivecraft.creativegates;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.massivecraft.creativegates.cmd.CmdCg;
import com.massivecraft.creativegates.entity.MConf;
import com.massivecraft.creativegates.entity.MConfColl;
import com.massivecraft.creativegates.entity.UConfColls;
import com.massivecraft.creativegates.entity.UGateColls;
import com.massivecraft.creativegates.index.IndexCombined;
import com.massivecraft.massivecore.Aspect;
import com.massivecraft.massivecore.AspectColl;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.Multiverse;

public class CreativeGates extends MassivePlugin
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	public final static Set<Material> VOID_MATERIALS = EnumSet.of(Material.AIR); 
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static CreativeGates i;
	public static CreativeGates get() { return i; }
	public CreativeGates()
	{
		CreativeGates.i = this;
		
		// Version Synchronized
		this.setVersionSynchronized(true);
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// Aspects
	private Aspect aspect;
	public Aspect getAspect() { return this.aspect; }
	public Multiverse getMultiverse() { return this.getAspect().getMultiverse(); }
	
	// Index
	private final IndexCombined index = new IndexCombined();
	public IndexCombined getIndex() { return this.index; };
	
	// Filling
	private boolean filling = false;
	public boolean isFilling() { return this.filling; }
	public void setFilling(boolean filling) { this.filling = filling; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void onEnableInner()
	{
		// Initialize Aspects
		this.aspect = AspectColl.get().get(Const.ASPECT, true);
		this.aspect.register();
		this.aspect.setDesc(
			"<i>What gates do exist.",
			"<i>What the config options are set to."
		);

		// Index
		this.getIndex().clear();
		
		// Activate
		this.activate(
			// Coll
			MConfColl.class,
			UConfColls.class,
			UGateColls.class,
		
			// Engine
			EngineMain.class,
			
			// Command
			CmdCg.class
		);
	
		// Schedule a permission update.
		// Possibly it will be useful due to the way Bukkit loads permissions.
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run()
			{
				MConf.get().updatePerms();
			}
		});
	}
	
	@Override
	public void onDisable()
	{
		this.getIndex().clear();
		super.onDisable();
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public static boolean isVoid(Material material)
	{
		return VOID_MATERIALS.contains(material);
	}
	
	public static boolean isVoid(Block block)
	{
		return isVoid(block.getType());
	}
	
}

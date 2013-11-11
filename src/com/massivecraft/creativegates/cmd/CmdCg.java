package com.massivecraft.creativegates.cmd;

import java.util.List;

import com.massivecraft.creativegates.CreativeGates;
import com.massivecraft.creativegates.Perm;
import com.massivecraft.creativegates.entity.MConf;
import com.massivecraft.mcore.cmd.HelpCommand;
import com.massivecraft.mcore.cmd.MCommand;
import com.massivecraft.mcore.cmd.VersionCommand;
import com.massivecraft.mcore.cmd.req.ReqHasPerm;

public class CmdCg extends MCommand
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public CmdCgWorld cmdCgWorld = new CmdCgWorld();
	public VersionCommand cmdCgVersion = new VersionCommand(CreativeGates.get(), Perm.CG_VERSION.node, "v", "version");
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdCg()
	{
		// Add SubCommands
		this.addSubCommand(HelpCommand.get());
		this.addSubCommand(this.cmdCgWorld);
		this.addSubCommand(this.cmdCgVersion);
		
		// Requirements
		this.addRequirements(ReqHasPerm.get(Perm.CG.node));
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public List<String> getAliases()
	{
		return MConf.get().aliasesCg;
	}
	
}

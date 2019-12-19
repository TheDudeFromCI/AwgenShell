[![Build Status](https://travis-ci.org/Wraithaven/AwgenShell.svg?branch=dev)](https://travis-ci.org/Wraithaven/AwgenShell)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=TheDudeFromCI_AwgenShell&metric=alert_status)](https://sonarcloud.io/dashboard?id=TheDudeFromCI_AwgenShell)
[![Coverage Status](https://coveralls.io/repos/github/TheDudeFromCI/AwgenShell/badge.svg?branch=dev)](https://coveralls.io/github/TheDudeFromCI/AwgenShell?branch=eada8ddd466d8d8b5087c7af80d5b560772ffeb4)
[![Latest Version](https://img.shields.io/github/release/Wraithaven/AwgenShell.svg)](https://github.com/Wraithaven/AwgenShell/releases)

---

# AwgenShell

An interactive and flexible command line and library for the Awgen Shell language.

---

## Summary

Awgen Shell is designed to be a simple, yet powerful, command line language that can be embedded within any Java application for a quick and easy setup. Awgen Shell follows as "everything-is-a-command" principal, to offer maximum flexibility to users for implementing within other applications with varying needs.

One of the core inspirations behind Awgen Shell is to ease the debugging process during long development cycles of large projects. New commands can be made to work with the underlaying project in just a single class, and can be combined with commands throuh piping, in direct commands, and more, to expotentially gather more information and process that information at runtime.

## Downloads

### Jar

The Awgen Shell releases can be found in the [releases tab](https://github.com/Wraithaven/AwgenShell/releases) for this repository. The entire language is packaged within a single jar file for maximum portability. Just add to your build path and you're all done.

### Maven

Awgen Shell is not yet in the Maven Central Repository, however, can still be used with Maven through plugins like [JitPack](https://jitpack.io/).

The Maven setup, using JitPack is done by adding JitPack to your repositories list in your POM file.

```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```
  
Then simple adding Awgen Shell as a dependency as such:
    
```xml
<dependency>
	<groupId>com.github.Wraithaven</groupId>
	<artifactId>AwgenShell</artifactId>
	<version> AGWEN SHELL VERSION </version>
	
	<!-- Note, sometimes versions fail to resolve in Jitpack. If this happens, using the first 10 characters of the commit hash also works. -->
	
</dependency>
```

## Getting Started

Working with Awgen Shell is very simple, and is largely covered in the [Getting Started](https://github.com/Wraithaven/AwgenShell/wiki/Getting-Started) page on the wiki. There you can find entry level through advanced level steps to understanding the handling of the front end of the Agen Shell language completely.

## API

The API is designed to be as light as possible, creating minimal overhead for developers.

### Running Commands

All commands are run within a Shell Environment instance. An environment is a collection of loaded commands and variables assosiated with a Command Sender.

```java
public class MyConsoleSender implements CommandSender
{
	@Override
	public String getName(){ return "Console"; }
	
	@Override
	public void println(String message){ System.out.println(message); }
}

// ...

MyConsoleSender sender = new MyConsoleSender();
ShellEnvironment env = new ShellEnvironment(sender);
```

Commands can be running by simply passing them to the Shell Environment afterwards.

```java
String cmd = "print 'Hello, world!'";

env.runCommand(cmd);
```

### Creating Commands

Creating commands is just as easy. A command is and object which extends the CommandHandler interface. Let's create a simple command which returns some property from a config file. (More details can be found on the wiki page [here](https://github.com/Wraithaven/AwgenShell/wiki/Creating-Commands)!)

```java
public class PropertyCommand implements CommandHandler
{
	private PropertyFile propertyFile;
	
	public PropertyCommand(PropertyFile propertyFile)
	{
		this.propertyFile = propertyFile;
	}
	
	@Override
	public String getName(){ return "property"; }
	
	@Override
	public String getAliases(){ return new String[]{ "prop" }; }
	
	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (args.length != 1)
		{
			env.getCommandSender().println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}
		
		String value = propertyFile.getProperty(args[0].getValue());
		
		return new CommandResult(value, true, false);
	}
}
```

Now we simply add our command to a module, and load it into the Shell Environment.

```java

Module module = new Module();
module.loadCommand(new PropertyCommand(propertyFile));

env.loadModule(module);
```

And we're all set! We can now run the property command within that shell environment!

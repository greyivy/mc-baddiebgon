![Baddie-B-Gon™](https://media.forgecdn.net/attachments/421/560/wordart.png "Baddie-B-Gon™")
**A Fabric mod providing an item that repels hostile mobs and an optional global mob filter (similar to [Bad Mobs for Forge](https://www.curseforge.com/minecraft/mc-mods/bad-mobs)).**


# Recipe


![Baddie-B-Gon™ Spray Recipe](https://media.forgecdn.net/attachments/421/529/recipe.png "Baddie-B-Gon™ Spray Recipe")

Baddie-B-Gon™ Spray


# Installation
Install on both client and server.


# Config
### Client
Use Mod Menu.

### Server
Create a `baddiebgon.json5` file containing the following:

```
{
    "effectiveDistanceSpray": 50,
    "banHostileMobRegex": ""
}
```

`effectiveDistanceSpray` determines for how far the Baddie-B-Gon™ spray item is effective.

`banHostileMobRegex` must be a Java-compatible regular expression. To ban all spiders and creepers, for example, use `".*(spider|creeper).*"`.

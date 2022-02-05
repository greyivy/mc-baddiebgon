![Baddie-B-Gon™](https://user-images.githubusercontent.com/5335625/148668162-95c8bf1a-78a9-4dc8-ae04-b5dcfe73409a.png)

**A Fabric mod providing an item that repels hostile mobs and an optional global mob filter (similar to [Bad Mobs for Forge](https://www.curseforge.com/minecraft/mc-mods/bad-mobs)).**


# Recipe

<img width="118" alt="Baddie-B-Gon™ Spray Recipe" src="https://user-images.githubusercontent.com/5335625/148668124-70d261ea-4f9e-4b8d-87ed-2108d7c3aaca.png">

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

`banHostileMobRegex` must be a Java-compatible regular expression. To ban all spiders and creepers, for example, use `.*(spider|creeper).*`.

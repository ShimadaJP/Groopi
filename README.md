# Groopi

Groovy scripting support for all versions

## Why Groopi?

JavaScript engine was removed from Java since Java 15. Thus, I made an expansion that can be used from any version of
Minecraft, Java.

Groovy also provides easier and more readable syntax than JavaScript, making scripting much easier and fun.

## Usage

```
/papi parse me %groopi_ <Groovy expression> %
```

`org.bukkit` package is automatically imported. Also, `player` and `resolver` variable is available.

### `Resolver` variable

You can resolve placeholder variables using `resolver#resolve` method.

For example, this will output 8

```
/papi parse me %groopi_ 2 * resolver.resolve("groopi_ 2 * 2").toInteger() %
```

This command is equal to the following command.

```
/papi parse me %groopi_ 2 * me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, "\pgroopi_ 2 * 2\p").toInteger() %
```

Note `\p` is automatically replaced by `%`.

## Examples

### Calculate 1000 * 20 + 100 - 24

#### Command

```
/papi parse me %groopi_ 1000 * 20 + 100 - 24 %
```

#### Result

```
20076
```

### Generate random value based on player's UUID

#### Command

```
/papi parse me %groopi_ new Random(player.uniqueId.leastSignificantBits).nextInt() %
```

#### Result

```
938320166
```

### Show all players

#### Command

```
/papi parse me %groopi_ Bukkit.getOnlinePlayers().stream().map {it->it.name}.toList() %
```

#### Result

```
[Notch]
```

### Calculate player hearts

#### Command

```
/papi parse me %groopi_ resolver.resolve("player_health").toDouble() / 2 %
```

#### Result

```
10
```

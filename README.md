WIP

# No Fun Allowed

Are your players having too much fun? Do you yearn for the simpler days of Alpha Minecraft where players gathered
resources to build a house to survive the night? Do you miss the days when massive farms that make the game super easy
for you weren't really a thing? I mean, this is **SURVIVAL** Minecraft, right!? Where's the surviving? Where's all the
effort people used to put in to get resources? At this point it might as well be creative mode, right?

Well look no further, fellow grumpy old man, oh don't I have a mod for you!  
With NFA, you can send your cheaty players back to 2011 by disabling aspects of gameplay which just aren't quite 
survival enough. No more raid farms giving you an unlimited supply of emeralds and redstone. No more iron golems
obsoleting the "mining" part of **MINE**craft. No more throwing those poor, innocent villagers into a 2x1 cell
to bleed their hard-earned resources dry. I mean, hello?? Geneva?? No more climate unfriendly animal farming!

#### TODO
- [ ] Bypass rules if player has the appropriate permission (using [lucko's permission API](https://github.com/lucko/fabric-permissions-api))
- [ ] Cloned shulkers don't drop shells (or reduce)
- [ ] Decrease loot if mobs are killed too quickly
- [x] Decrease XP dropped if mobs are killed too quickly
- [x] Disable golem loot entirely
- [ ] Disable golem loot if spawned by fear
- [x] Disable totems of undying
- [x] Don't give bad omen if patrol leader spawned by raid<sup>1</sup>
- [x] Limit zombie villager curing discounts
- [x] Mobs only drop XP/loot if killed by player
- [ ] Mobs only drop XP if killed in the wild<sup>2</sup>
- [x] No bedrock breaking
- [x] No building above nether roof
- [x] No TNT duping
- [ ] No gravity block duping
- [x] Overcuring villagers damages player reputation
- [x] Prevent animal breeding if too many animals nearby
- [x] Prevent villagers restocking if they haven't moved
- [ ] Reduce crop growth speed if crop farm too big<sup>2</sup>
- [x] Require bed to have blocks above it to sleep
- [x] Require light to sleep

<sup>1</sup> Bad omen is only given if the patrol leader isn't in an active raid, but raid farms have the raid spawn far away
from its center, having the patrol leader "leave" the raid, allowing the player to stack up on bad omen. This change prevents
patrol leaders from giving bad omen even after they've left the raid.

<sup>2</sup> Not really sure of an elegant way to do this. Advice would be appreciated.

## FAQ

#### How can I install this?
This is a work in progress and isn't ready.

#### How can I install this on my Paper server?
You don't. Try [Purpur](https://purpurmc.org/) if you want to configure gameplay.

#### Why did you make this?
This mod is mostly just tongue-in-cheek. To be clear, I don't advocate for this reductive and restrictive gameplay.
Being able to understand the mechanics of the game and using it to your advantage to farm resources is part of what
makes the game great.

So why did I make this then? I was reading posts online from server operators asking for ways to
stop their players from farming and making them play the game "legitmately", which I found hilarious and just outright
silly. After a bit of pondering, I thought "what if there were actually a mod that just straight up prevented farming
and all the fun aspects of the game and just left you the barebones of survival of 10 years ago". I decided to actually
try and make such a mod, mostly as a joke, but I'm sure someone will find this and go "WOW this is just what I wanted!"

package com.neponies;

import java.util.List;
import java.util.Random;
import java.util.UUID;



public class PonyNames {


    public static String generateFirstName(UUID uuid) {
        int index = deterministicIndex(uuid, PONY_FIRST_NAMES.size(), 0);
        return PONY_FIRST_NAMES.get(index);
    }

    public static String generateSecondName(UUID uuid) {
        int index = deterministicIndex(uuid, PONY_SECOND_NAMES.size(), 1);
        return PONY_SECOND_NAMES.get(index);
    }


    private static int deterministicIndex(UUID uuid, int listSize, int salt) {
        long most = uuid.getMostSignificantBits();
        long least = uuid.getLeastSignificantBits();
        long combined = most ^ least ^ salt; // XOR с солью
        return (int) (Math.abs(combined) % listSize);
    }


    private static final List<String> PONY_FIRST_NAMES = List.of(
            // --- ORIGINAL ---
            "Apple", "Baked", "Banana", "Berry", "Candy", "Caramel", "Carrot",
            "Cherry", "Cheese", "Cinnamon", "Cookie", "Cream", "Flurry", "Grape", "Half Baked", "Lemon",
            "Peachy", "Pumpkin", "Strawberry", "Sugar", "Sweet", "Sweetie",
            "Amethyst", "Diamond", "Emerald", "Ruby", "Golden", "Goldie", "Silver", "Jewel",
            "Royal", "Gala", "Fancy", "Prism", "Swanky", "Opal",
            "Blue", "Blues", "Cerulean", "Green", "Lavender", "Lilac", "Periwinkle", "Pinkie", "Purple", "Red",
            "Rainbow", "Neon",
            "Bright", "Starlight", "Sunlight", "Sunny", "Sunset", "Sunshine", "Sunshower", "Twilight",
            "Babs", "Bae", "Beauty", "Big", "Big Daddy", "Charity", "Cloud", "Cloudy", "Cotton",
            "Daisy", "Electric", "Feldspar", "Granite", "Fluffy", "Hayseed", "Holly", "Igneous Rock", "Limestone",
            "Lotus", "Lucky", "Lyra", "Merry", "Perfect", "Pound", "Magnet", "Marble", "Maud",
            "Stormy", "Tall", "Tender", "Top", "Vidala",

            // --- NEW (FRUITS & SWEETS) ---
            "Apricot", "Blackberry", "Coconut", "Fig", "Kiwi", "Mango", "Papaya", "Plum", "Pineapple", "Raspberry",
            "Vanilla", "Butterscotch", "Mocha", "Latte", "Cocoa", "Marzipan", "Nougat", "Toffee", "Maple", "Syrup",

            // --- NEW (GEMS & SHINY) ---
            "Crystal", "Sapphire", "Topaz", "Onyx", "Obsidian", "Quartzite", "Spinel", "Tourmaline", "Peridot", "Garnet",
            "Shiny", "Glitter", "Sparkly", "Gleam", "Lustrous", "Dazzle", "Radiant", "Aurora", "Polished", "Moonstone",

            // --- NEW (COLORS & LIGHT) ---
            "Indigo", "Cobalt", "Scarlet", "Ivory", "Amber", "Teal", "Turquoise", "Cyan", "Violet", "Crimson",
            "Glow", "Beam", "Shinyhooves", "Glimmer", "Lumen", "Shimmerlight", "Daybreak", "Starbright", "Midnight", "Aurorae",

            // --- NEW (NATURE) ---
            "Blossom", "Rosebud", "Petunia", "Thistle", "Fern", "Poppy", "Ivy", "Clover", "Acorn", "Hazel",
            "Meadow", "Breeze", "Windy", "Rainy", "Snowy", "Frosty", "Evergreen", "Brook", "River", "Pinecone",

            // --- NEW (QUIRKY/FUN) ---
            "Choco", "Muffin", "Cupcake", "Donut", "Sprinkle", "Bubble", "Popcorn", "Marshmallow", "Pickle", "Noodle",
            "Doodle", "Giggles", "Snuggles", "Sparkplug", "Zippy", "Zoomy", "Jolly", "Bouncy", "Goofy", "Wiggly"
    );


    private static final List<String> PONY_SECOND_NAMES = List.of(
            // --- ORIGINAL ---
            "Apple", "Apples", "Applesauce", "Berry", "Cake", "Cider", "Cinnamon", "Crème", "Delicious", "Delight",
            "Dumpling", "Fritter", "Grape", "Harvest", "Honey", "Jellius", "Mint", "Munchies", "Orange", "Peachbottom",
            "Pie", "Puff", "Sandwich", "Spices", "Strudel", "Sweet", "Tart", "Truffle", "Waffle", "Wheat",
            "Brass", "Brioche", "Bullion", "Gala", "Gem", "Jewel", "Jubilee", "Tiara", "Velvet", "Rich",
            "Flare", "Lights", "Luster", "Pink", "Radiance", "Rays", "Shimmer", "Shine", "Sparkle", "Sunrise",
            "Fetlock", "Flanks", "Letrotski", "Harshwhinny", "Hooffield", "Horseshoepin", "Mare", "Maresbury", "Nandermane",
            "Appleby", "Belle", "Bloom", "Blossom", "Bulb", "Bumpkin", "Chaser", "Clover", "Cobbler", "Crisp",
            "Dash", "Daze", "Dreams", "Drops", "Fizzy", "Flora", "Fluff", "Frames", "Gavel", "Glider",
            "Heart", "Hearts", "Heartstrings", "Hugger", "Joy", "Kindheart", "Leaves", "McColt", "McIntosh", "Melody",
            "Moon", "Noteworthy", "Pace", "Pansy", "Pants", "Petals", "Pin", "Quartz", "Redheart", "Ribbon",
            "Riff", "Rose", "Seed", "Shill", "Shot", "Skies", "Sky", "Smile", "Smiles", "Splash",
            "Split", "Spoon", "Tooth", "Top", "Twist", "Valet", "Wave", "Wig",

            // --- NEW (FAMILY / SOCIAL) ---
            "Song", "Strings", "Tune", "Chime", "Lyric", "Verse", "Melodies", "Rhythm", "Tempo", "Harmony",
            "Hooves", "Tail", "Mane", "Neigh", "Snout", "Gallop", "Trot", "Canter", "Stride", "Step",

            // --- NEW (FOOD & DRINK) ---
            "Buttermilk", "Pancake", "Scone", "Brownie", "Croissant", "Pudding", "Parfait", "Jam", "Jellybean", "Syrupcake",
            "Teacup", "Kettle", "Cocoa", "Chocohoof", "Lattecup", "Espresso", "Biscuit", "Poppyroll", "Bagel", "Toast",

            // --- NEW (NATURE) ---
            "Storm", "Cloud", "Rain", "Mist", "Fog", "Thunder", "Lightning", "Snowdrop", "Icicle", "Frost",
            "Leaf", "Branch", "Twig", "Root", "Stone", "Pebble", "Boulder", "Canyon", "Ridge", "Valley",

            // --- NEW (MAGICAL / CELESTIAL) ---
            "Star", "Starlight", "Nova", "Eclipse", "Comet", "Meteor", "Astro", "Galaxy", "Nebula", "Cosmos",
            "Charm", "Rune", "Hex", "Aura", "Glow", "Halo", "Mystic", "Whisper", "Wish", "Dreamer",

            // --- NEW (QUIRKY/FUN) ---
            "Giggle", "Snuggle", "Tickle", "Chuckles", "Bubbles", "Wiggle", "Nibbles", "Scribble", "Doodle", "Jelly",
            "Zap", "Zoom", "Whizz", "Bang", "Fizz", "Pop", "Snap", "Crackle", "Boom", "Clatter"
    );

}

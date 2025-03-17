package hi.verkefni.vinnsla;

public enum Flokkur {
    EXERCISE("🚴"),
    WORK("📊"),
    RELAX("🧘"),
    TV("📺"),
    READ("📚"),
    EVENT("🎭"),
    CODE("💻"),
    COFFEE("☕️"),
    EAT("🍽"),
    SHOP("🛒"),
    SLEEP("😴");

    private String emoji;

    Flokkur(String emoji)
    {
        this.emoji = emoji;
    }

    public String getEmoji()
    {
        return this.emoji;
    }
}
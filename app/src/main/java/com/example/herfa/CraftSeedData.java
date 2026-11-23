package com.example.herfa;

/**
 * The crafts the app starts with.
 *
 * DRAFT: Member 6 checks every entry against a source and replaces these
 * with the final list. Region must be exactly one of:
 * الشرقية، الوسطى، الغربية، الجنوبية، الشمالية
 * imagePath is the picture's name in res/drawable (no ".png").
 */
public class CraftSeedData {

    /** Returns the starter crafts, inserted once when the database is created. */
    static Craft[] getStarterCrafts() {
        return new Craft[]{
                new Craft("السدو",
                        "نسيج بدوي تقليدي تصنعه النساء من صوف الأغنام ووبر الإبل وشعر الماعز، "
                                + "ويتميز بنقوشه الهندسية وألوانه الزاهية، ويُستخدم في بيوت الشعر والمفارش.",
                        "النول الأرضي، المغزل، الصوف",
                        "الشمالية",
                        "sadu"),
                new Craft("سفّ الخوص",
                        "نسج سعف النخيل بعد تجفيفه لصنع السلال والحصر والمراوح وأغطية الطعام.",
                        "سعف النخيل، المخرز، السكين",
                        "الشرقية",
                        "palm_weaving"),
                new Craft("الفخار",
                        "تشكيل الطين على عجلة الفخار لصنع الأواني والجرار ثم حرقها في الفرن.",
                        "الطين، عجلة الفخار، الفرن",
                        "الشرقية",
                        "pottery"),
                new Craft("القط العسيري",
                        "فن تزيّن فيه النساء جدران البيوت في عسير بنقوش هندسية ملونة.",
                        "الأصباغ، الفُرش",
                        "الجنوبية",
                        "qatt")
        };
    }
}

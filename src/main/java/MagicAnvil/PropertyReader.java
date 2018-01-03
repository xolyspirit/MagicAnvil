package MagicAnvil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class PropertyReader {
    private static PropertyReader ourInstance = new PropertyReader();
    private HashMap<String,Material> materials;
    private HashMap<String, Decoration> decorations;
    private JsonParser parser;

    private PropertyReader(){
        materials = new HashMap<String, Material>();
        decorations = new HashMap<String, Decoration>();
        parser = new JsonParser();

        try {
            Object materialsObject = parser.parse(new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("MagicAnvil/resources/Materials.json"))));
            Object decorationsObject = parser.parse(new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("MagicAnvil/resources/Decorations.json"))));
            JsonObject temp = (JsonObject) materialsObject;
            JsonArray mobj = temp.getAsJsonArray("materials");
            temp = (JsonObject)decorationsObject;
            JsonArray dobj = temp.getAsJsonArray("decorations");
            
            //заполняем мапу материалов с помощью Json
            for (JsonElement quest:mobj) {
                JsonObject obj = quest.getAsJsonObject();
                Material mat = new Material();
                
                //заполняем простые поля
                mat.setMaterialName(obj.get("materialName").getAsString());
                mat.setCraftedOfName(obj.get("craftedOfName").getAsString());
                mat.setAddedToCraftName(obj.get("addedToCraftName").getAsString());
                mat.setQuality(Material.Quality.valueOf(obj.get("quality").getAsString()));
                mat.setQualityMultiplicator(obj.get("qualityMultiplicator").getAsDouble());
                mat.setStat(Material.Stats.valueOf(obj.get("stat").getAsString()));
                mat.setColor(Material.Colors.valueOf(obj.get("color").getAsString()));
                mat.setMaterialType(Material.MaterialType.valueOf(obj.get("materialType").getAsString()));

                //Заполняем мапу элементов
                JsonObject els = obj.getAsJsonObject("elements");
                HashMap<Material.Elements, Integer> elems = new HashMap<Material.Elements, Integer>();
                int sum = 0;
                Byte tempByte;
                for (String s: els.keySet()) {
                    tempByte = els.get(s).getAsByte();
                    sum += tempByte;
                    if (sum <= 100) {
                        elems.put(Material.Elements.valueOf(s), tempByte.intValue());
                    } else {
                        throw new Exception("Неправильно указаны проценты стихий! Не может быть больше 100%!");
                    }
                }
                if(sum !=100 &&!elems.containsKey(Material.Elements.BASE)){
                    elems.put(Material.Elements.BASE, 100-sum);
                }
                else if(sum !=100 &&elems.containsKey(Material.Elements.BASE)){
                    throw new Exception("Неправильно указаны проценты стихий! Сумма должна быть 100%!");
                }

                mat.setElements(elems);
                
                //Заполняем массив эффектов
                JsonArray effs = obj.getAsJsonArray("effects");
                ArrayList<Effect> effects = new ArrayList<Effect>();
                for (JsonElement ef: effs) {
                    Effect effect = new Effect();
                    effect.setEffectName(ef.getAsJsonObject().get("effectName").getAsString());
                    effect.setEffectDescription(ef.getAsJsonObject().get("effectDescription").getAsString());
                    effect.setChance(ef.getAsJsonObject().get("chance").getAsByte());
                    effect.setEffectType(Effect.EffectType.valueOf(ef.getAsJsonObject().get("effectType").getAsString()));
                    effect.setElement(Material.Elements.valueOf(ef.getAsJsonObject().get("element").getAsString()));
                    effects.add(effect);
                }
                mat.setEffects(effects);

                //добавляем заполненный материал в нашу мапу
                materials.put(mat.getMaterialName(),mat);
            }
            
            //заполняем мапу украшений с помощью Json
            for (JsonElement quest:dobj) {
                JsonObject obj = quest.getAsJsonObject();
                Decoration decor = new Decoration();

                //заполняем название украшения
                decor.setName(obj.get("name").getAsString());
                decor.setAddedToCraft(obj.get("addedToCraft").getAsString());
                //если указан материал - заполняем его, если нет, пропускаем
                if (obj.has("material")) {
                    decor.setMaterial(materials.get(obj.get("material").getAsString()));
                }
                //заполняем поля эффекты
                JsonObject ef = obj.getAsJsonObject("effect");
                Effect effect = new Effect();
                effect.setEffectName(ef.get("effectName").getAsString());
                effect.setEffectDescription(ef.get("effectDescription").getAsString());
                effect.setChance(ef.get("chance").getAsByte());
                effect.setEffectType(Effect.EffectType.valueOf(ef.get("effectType").getAsString()));
                effect.setElement(Material.Elements.valueOf(ef.get("element").getAsString()));
                decor.setEffect(effect);

                //добавляем заполненное украшение в нашу мапу
                decorations.put(decor.getName(), decor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public HashMap<String, Material> getMaterials() {
        return materials;
    }

    public void setMaterials(HashMap<String, Material> materials) {
        this.materials = materials;
    }

    public HashMap<String, Decoration> getDecorations() {
        return decorations;
    }

    public void setDecorations(HashMap<String, Decoration> decorations) {
        this.decorations = decorations;
    }

    public static PropertyReader getInstance() {
        return ourInstance;
    }
}



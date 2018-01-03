package MagicAnvil;

import java.util.ArrayList;
import java.util.HashMap;

/**Содержит в себе методы обработки материала. Создает предмет из списка предложенных материалов**/
public class ItemCrafter {
    private static ItemCrafter ourInstance = new ItemCrafter();
    private ArrayList<Material.MaterialType> allowedMaterials;
    private HashMap<Material.Elements, Integer> resultElements;
    private ArrayList<Effect> resultEffects;

/**Метод создающий конкретно снаряжение**/
    public String equipCraft(Equipment.EquipTypes equipType, Material mainMaterial, Material additionalMaterial, Decoration decor){
        StringBuilder result = new StringBuilder();

        Material.MaterialType maintype = mainMaterial.getMaterialType();
        Material.MaterialType addtype = additionalMaterial.getMaterialType();
        //Проверяем установленные материалы на соответствие списку, если да, то составляем название предмета
        if(allowedMaterials.contains(maintype)&&allowedMaterials.contains(addtype)) {
                //Если использован только один материал
            if (!mainMaterial.getMaterialName().equals(additionalMaterial.getMaterialName())) {
                result.append(equipType +
                        mainMaterial.getCraftedOfName() + " и" +
                        additionalMaterial.getAddedToCraftName() +
                        decor.getAddedToCraft() + "\n");
                //добавляем два цвета
                result.append("Цвет: " + mainMaterial.getColor() +
                        " с вкраплениями " + additionalMaterial.getColor().addedName() + "\n");

                //Если использованно два материала
            } else {
                result.append(equipType +
                        mainMaterial.getCraftedOfName() +
                        decor.getAddedToCraft() + "\n");
                //добавляем цвет
                result.append("Цвет: " + mainMaterial.getColor() + "\n");
            }

            //Подготавливаем посчет характеристик
            result.append("Дает: " + "\n");
            resultElements = new HashMap<Material.Elements, Integer>();
            Integer multiplier;

            //собираем характеристики главного материала
            HashMap<Material.Elements,Integer> mainElements = mainMaterial.getElements();
            for (Material.Elements el: mainElements.keySet()) {
                //учитываем качественный мультипликатор материала
                multiplier = (int)Math.round(mainElements.get(el)*mainMaterial.getQualityMultiplicator()/mainMaterial.getQuality().getQualityDivider());
                resultElements.put(el,multiplier);

            }

            //собираем характеристики дополнительного материала, если материалы одинаковые, то второй материал не учитываем
           if(!mainMaterial.getMaterialName().equals(additionalMaterial.getMaterialName())) {
               HashMap<Material.Elements, Integer> addElements = additionalMaterial.getElements();
                for (Material.Elements el : addElements.keySet()) {
                    //учитываем качественный мультипликатор материала
                    multiplier = (int)Math.round(addElements.get(el)*additionalMaterial.getQualityMultiplicator()/additionalMaterial.getQuality().getQualityDivider());
                    if (resultElements.containsKey(el)) {
                        resultElements.put(el, (mainElements.get(el) + (multiplier / 2)));
                    } else {
                        resultElements.put(el, multiplier / 2);
                    }
                }
            }

            //собираем характеристики декора, материал может быть не указан, потому проверяем
            if (decor.getMaterial()!=null){
                HashMap<Material.Elements, Integer> decorElements = decor.getMaterial().getElements();
                for (Material.Elements el : decorElements.keySet()) {
                    //учитываем качественный мультипликатор материала
                    multiplier = (int)Math.round(decorElements.get(el)*decor.getMaterial().getQualityMultiplicator()/decor.getMaterial().getQuality().getQualityDivider());
                    if (resultElements.containsKey(el)) {
                        resultElements.put(el, (decorElements.get(el) + multiplier ));
                    } else {
                        resultElements.put(el, decorElements.get(el) / 4);
                    }
                }
            }

            //Добавим элемент случайности к генерации характеристик. Чем больше характеристик, тем больше возможное оклонение
            Integer scatter = 0;
            Integer global;
            for (Material.Elements el : resultElements.keySet()) {
                scatter+= resultElements.get(el);
            }
            global = scatter;
            scatter = scatter/20;

           //Преобразуем данные в конкретные статы
            result.append((Math.round((resultElements.get(Material.Elements.BASE)+ random(-scatter, scatter))/5)  ) + " к броне" + "\n");
            for (Material.Elements el:resultElements.keySet()) {
                resultElements.put(el,random(-scatter, scatter)+ resultElements.get(el));
                if(el== Material.Elements.BASE) {
                    resultElements.put(el,resultElements.get(el)/8);
                }
                if(resultElements.get(el) > 0) {
                    result.append("+" + resultElements.get(el) +el.getAssociatedStat().addedStat() + "\n");
                }
                else if (resultElements.get(el) < 0){
                    result.append(resultElements.get(el) + el.getAssociatedStat().addedStat() + "\n");
                }
            }

            //определяем общее качество предмета
            Integer globalResult =0;
            for (Integer el:resultElements.values()) {
                globalResult += el;
            }
            Integer odds = global - globalResult;
            String rarity;
            if(odds < 60)
                rarity = "Эпик!";
            else if(odds < 70)
                rarity = "Прекрасное";
            else if (odds < 80)
                rarity = "Хорошее";
            else if(odds < 90)
                rarity = "Плохое";
            else
                rarity = "Очень плохое";
            result.append("Качество предмета: " + rarity + "\n");

        }
        else{
            result.append("Из этих материалов нельзя сделать " + equipType.toString().toLowerCase());
        }

        //Добавляем эффекты
        //от главного материала
        for (Effect ef:mainMaterial.getEffects()) {
            if (ef.getEffectType()== Effect.EffectType.DEFENSE) {
                result.append(ef.getNameWithPercentages(resultElements.get(ef.getElement())/10));
            }
        }

        //от дополнительного материала
        for (Effect ef:additionalMaterial.getEffects()) {
            if (ef.getEffectType()== Effect.EffectType.DEFENSE) {
                result.append(ef.getNameWithPercentages(resultElements.get(ef.getElement())/10));
            }
        }

        //от украшений
        for (Effect ef:decor.getMaterial().getEffects()) {
            if (ef.getEffectType()== Effect.EffectType.DEFENSE) {
                result.append(ef.getNameWithPercentages(resultElements.get(ef.getElement())/10));
            }
        }

        return result.toString();
    }
    private Integer random(Integer min, Integer max){
        Integer result;
        result = (int)Math.round(min + Math.random()*((max-min) +1));
        return result;
    }


    public static ItemCrafter getInstance() {
        return ourInstance;

    }

    private ItemCrafter() {
        allowedMaterials = new ArrayList<Material.MaterialType>();
        allowedMaterials.add(Material.MaterialType.CLOTH);
        allowedMaterials.add(Material.MaterialType.LEATHER);
        allowedMaterials.add(Material.MaterialType.METAL);
    }
}

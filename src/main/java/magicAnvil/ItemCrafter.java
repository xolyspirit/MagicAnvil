package magicAnvil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**Содержит в себе методы обработки материала. Создает предмет из списка предложенных материалов
 * на данный момент создает предметы в виде строк, но в дальнейшем будет создавать обьекты**/

public class ItemCrafter {
    /**это нужно для поддержания паттерна одиночка**/
    private static ItemCrafter ourInstance = new ItemCrafter();
    /**Нельзя сделать штаны из песка, поэтому здесь перечисление материалов доступных для пользования**/
    private ArrayList<Material.MaterialType> allowedEquipmentMaterials;
    /**общий пакет свойств предмета**/
    private HashMap<Material.Elements, Integer> resultElements;



/**Метод создающий конкретно снаряжение**/
    public Equipment equipCraft(Equipment.EquipTypes equipType, Material mainMaterial, Material additionalMaterial, Decoration decor){
        //подготавливаем переменные для работы
        //производимая в данный момент вещь
        Equipment craftedEquip = new Equipment();
        craftedEquip.setEquipType(equipType);
        //строка описания, мы в итоге внедрим ее в нашу вещь
        StringBuilder result = new StringBuilder();
        //тип основного материала
        Material.MaterialType maintype = mainMaterial.getMaterialType();
        //тип дополнительного материала
        Material.MaterialType addtype = additionalMaterial.getMaterialType();
        //Проверяем установленные материалы на соответствие списку, если да, то составляем название предмета
        if(allowedEquipmentMaterials.contains(maintype)&&allowedEquipmentMaterials.contains(addtype)) {
                //Если использовано два материала
            if (!mainMaterial.getMaterialName().equals(additionalMaterial.getMaterialName())) {
                result.append(equipType +
                        mainMaterial.getCraftedOfName() + " и" +
                        additionalMaterial.getAddedToCraftName() +
                        decor.getAddedToCraft() + "\n");
                //добавляем два цвета в описание предмета
                result.append("Цвет: " + mainMaterial.getColor() +
                        " с вкраплениями " + additionalMaterial.getColor().addedName() + "\n");

                //Если использован один материал
            } else {
                result.append(equipType +
                        mainMaterial.getCraftedOfName() +
                        decor.getAddedToCraft() + "\n");
                //добавляем цвет в описание предмета
                result.append("Цвет: " + mainMaterial.getColor() + "\n");
            }

            //Заносим данные о цвете в сам предмет
            craftedEquip.setMainColor(mainMaterial.getColor());
            craftedEquip.setAdditionalColor(additionalMaterial.getColor());

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
            //сначала готовим мапу статов для предмета
            HashMap<Material.Stats,Integer> stats = new HashMap<Material.Stats, Integer>();

            //отдельно считаем броню, так как она зависит от базовой характеристики
            Integer temp = (Math.round((resultElements.get(Material.Elements.BASE)+ random(-scatter, scatter))/5));
            result.append(temp + " к броне" + "\n");
            //заносим данные о броне в подготовленную мапу
            stats.put(Material.Stats.ARMOR,temp);

            //считаем статы
            for (Material.Elements el:resultElements.keySet()) {
               resultElements.put(el,random(-scatter, scatter)+ resultElements.get(el));
                //Нормализуем показатель телосложения, чтобы он не был чрезмерно большим
                if(el== Material.Elements.BASE) {
                    resultElements.put(el,resultElements.get(el)/8);
                }
                //после нормализации добавляем
                //если стат положительный
                if(resultElements.get(el) > 0) {
                    //заносим данные о конкретном стате в описание
                    result.append("+" + resultElements.get(el) + el.getAssociatedStat().addedStat() + "\n");
                    //заносим данные о конкретном стате в мапу предмета
                    stats.put(el.getAssociatedStat(),resultElements.get(el));
                }
                //если стат отрицательный
                else if (resultElements.get(el) < 0){
                    //заносим данные о конкретном стате в описание
                    result.append(resultElements.get(el) + el.getAssociatedStat().addedStat() + "\n");
                    //заносим данные о конкретном стате в мапу предмета
                    stats.put(el.getAssociatedStat(),resultElements.get(el));
                }
            }
            //заносим заполненную мапу в предмет
            craftedEquip.setStats(stats);

            //определяем общее качество предмета
            Integer globalResult =0;
            for (Integer el:resultElements.values()) {
                globalResult += el;
            }

            //В зависимости от разницы, между средним возможным и фактически получившимся предметом
            // - выставляется оценка качества
            Integer odds = global - globalResult;
            Material.Quality rarity;
            if(odds < 65)
                rarity = Material.Quality.IDEAL;
            else if(odds < 70)
                rarity = Material.Quality.GOOD;
            else if (odds < 90)
                rarity = Material.Quality.COMMON;
            else
                rarity = Material.Quality.BAD;

            //заносим данные о качестве в предмет
            craftedEquip.setQuality(rarity);
            //заносим данные о качестве в описание
            result.append("Качество предмета: " + rarity + "\n");

        }
        else{
            result.append("Из этих материалов нельзя сделать " + equipType.toString().toLowerCase());
        }

        //Добавляем эффекты только в том случае, если они применимы к снаряжению
        // - то есть имеют тип DEFENSE
        //подготавливаем Массив эффектов, для отправки в предмет
        HashSet<Effect> effects = new HashSet<Effect>();
        //от главного материала
        for (Effect ef:mainMaterial.getEffects()) {
            if (ef.getEffectType()== Effect.EffectType.DEFENSE) {
                //заносим эффект в описание
                result.append(ef.getNameWithPercentages(resultElements.get(ef.getElement())/10));
                //заносим эффект в списк эффектов предмета
                effects.add(ef);
            }
        }

        //от дополнительного материала
        for (Effect ef:additionalMaterial.getEffects()) {
            if (ef.getEffectType()== Effect.EffectType.DEFENSE) {
                //заносим эффект в описание
                result.append(ef.getNameWithPercentages(resultElements.get(ef.getElement())/10));
                //заносим эффект в списк эффектов предмета
                effects.add(ef);
            }
        }

        //от украшений
        for (Effect ef:decor.getMaterial().getEffects()) {
            if (ef.getEffectType()== Effect.EffectType.DEFENSE) {
                //заносим эффект в описание
                result.append(ef.getNameWithPercentages(resultElements.get(ef.getElement())/10));
                //заносим эффект в списк эффектов предмета
                effects.add(ef);
            }
        }
        craftedEquip.setDescription(result.toString());
        return craftedEquip;
    }

    /**стандартный рандом. дает челые числа в диапазоне мин-макс**/
    private Integer random(Integer min, Integer max){
        Integer result;
        result = (int)Math.round(min + Math.random()*((max-min) +1));
        return result;
    }

    private ItemCrafter() {
        allowedEquipmentMaterials = new ArrayList<Material.MaterialType>();
        allowedEquipmentMaterials.add(Material.MaterialType.CLOTH);
        allowedEquipmentMaterials.add(Material.MaterialType.LEATHER);
        allowedEquipmentMaterials.add(Material.MaterialType.METAL);
    }

    public static ItemCrafter getInstance() {
        return ourInstance;

    }
}

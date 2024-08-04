package eapli.base.questionnaire.domain;

import org.hibernate.cfg.CollectionSecondPass;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionnaireReport {

    private final Integer universe;
    private final Integer totalAnswers;
    private final Long answersPer100;
    private final Map<String,List<List<String>>> singleChoice;
    private final Map<List<String>,List<List<String>>> multipleChoice;
    private final Map<List<String>,List<List<String>>> sortingOptions;
    private final Map<List<String>,List<List<String>>> scalingOptions;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public QuestionnaireReport (Integer universe,Integer answersPer100,Map<String, List<List<String>>> singleChoice, Map<List<String>,
            List<List<String>>> multipleChoice, Map<List<String>, List<List<String>>> sortingOptions,
            Map<List<String>, List<List<String>>> scalingOptions) {
        this.answersPer100 = Long.valueOf(answersPer100);
        this.universe = universe;
        this.singleChoice = singleChoice;
        this.multipleChoice = multipleChoice;
        this.sortingOptions = sortingOptions;
        this.scalingOptions = scalingOptions;
        int total1 = 0;
        for (String set : singleChoice.keySet()) {
            total1 = total1 + singleChoice.get(set).size();
        }
        int total2 = 0;
        for (List <String> set : multipleChoice.keySet()) {
            total2 = total2 + multipleChoice.get(set).size();
        }
        int total3 = 0;
        for (List <String> set : sortingOptions.keySet()) {
            total3 = total3 + sortingOptions.get(set).size();
        }
        int total4 = 0;
        for (List <String> set : scalingOptions.keySet()) {
            total4 = set.size();
            break;
        }
        this.totalAnswers = total1 + total2 + total3 + total4;
    }

    public String reportPrint () {
        return "Universe: " + universe +
                "\nCostumers Answers: " + universe + " of " + answersPer100 + " possible"+
                "\nTotal Questions Answered: " + totalAnswers +
                "\n-----------------------\nSingle Choice\n\n" + generateSingleChoiceReport() +
                "\n-----------------------\nMultiple Choice\n\n" + generateMultipleChoiceReport() +
                "\n-----------------------\nSorting Options\n\n" + generateSortingOptionsReport() +
                "\n-----------------------\nScaling Options\n\n" + generateScalingOptionsReport();
    }

    public String generateSortingOptionsReport(){
        int total = 0;
        for (List <String> set : sortingOptions.keySet()) {
            total = total + sortingOptions.get(set).size();
        }
        StringBuilder aux = new StringBuilder("");
        float first1 = 0; float first2 = 0; float first3 = 0; float first4 = 0;float first5 = 0;
        float second1 = 0; float second2 = 0;float second3 = 0; float second4 = 0;float second5 = 0;
        float third1 = 0; float third2 = 0; float third3 = 0; float  third4 = 0;float  third5 = 0;

        for (List<String> set : sortingOptions.keySet()){
            if(set.get(0).equals("1")){
                first1 = first1 + sortingOptions.get(set).size();
            }
            if(set.get(1).equals("1")){
                second1= second1 + sortingOptions.get(set).size();
            }
            if(set.get(2).equals("1")){
                third1= third1 + sortingOptions.get(set).size();
            }
            if(set.get(0).equals("2")){
                first2= first2 + sortingOptions.get(set).size();
            }
            if(set.get(1).equals("2")){
                second2=  second2 + sortingOptions.get(set).size();
            }
            if(set.get(2).equals("2")){
                third2 = third2 + sortingOptions.get(set).size();
            }
            if(set.get(0).equals("3")){
                first3= first3 + sortingOptions.get(set).size();
            }
            if(set.get(1).equals("3")){
                second3= second3 + sortingOptions.get(set).size();
            }
            if(set.get(2).equals("3")){
                third3= third3 + sortingOptions.get(set).size();
            }
            if(set.get(0).equals("4")){
                first4 =  first4 + sortingOptions.get(set).size();
            }
            if(set.get(1).equals("4")){
                second4= second4 + sortingOptions.get(set).size();
            }
            if(set.get(2).equals("4")){
                third4 =  third4 + sortingOptions.get(set).size();
            }
            if(set.get(0).equals("5")){
                first5= first5 + sortingOptions.get(set).size();
            }
            if(set.get(1).equals("5")){
                second5 = second5 + sortingOptions.get(set).size();
            }
            if(set.get(2).equals("5")) {
                third5 =  third5 + sortingOptions.get(set).size();
            }

        }

        Map<String,Float> first = new HashMap<>();
        first.put("1",first1/total * 100); first.put("2",first2/total * 100);first.put("3",first3/total * 100);first.put("4",first4/total * 100);first.put("5",first5/total * 100);
        List<Map.Entry<String, Float>> list1 = new ArrayList<>(first.entrySet());
        list1.sort(Map.Entry.comparingByValue());
        aux.append("First\n");
        for (int i = 0; i < 5; i++){
            aux.append("OPTION ").append(list1.get(list1.size() - i - 1).getKey()).append(" - ").append(decimalFormat.format(list1.get(list1.size() - i - 1).getValue())).append(" %\n");
        }

        Map<String,Float> second = new HashMap<>();
        second.put("1",second1/total * 100);   second.put("2",second2/total * 100);  second.put("3",second3/total * 100);  second.put("4",second4/total * 100);  second.put("5",second5/total * 100);
        List<Map.Entry<String, Float>> list2 = new ArrayList<>(second.entrySet());
        list2.sort(Map.Entry.comparingByValue());
        aux.append("\nSecond\n");
        for (int i = 0; i < 5; i++){
            aux.append("OPTION ").append(list2.get(list2.size() - i - 1).getKey()).append(" - ").append(decimalFormat.format(list2.get(list2.size() - i - 1).getValue())).append(" %\n");
        }

        aux.append("\nThird\n");
        Map<String,Float> third = new HashMap<>();
        third.put("1",third1/total * 100);third.put("2",third2/total * 100);third.put("3",third3/total * 100);third.put("4",third4/total * 100);third.put("5",third5/total * 100);
        List<Map.Entry<String, Float>> list3 = new ArrayList<>(third.entrySet());
        list3.sort(Map.Entry.comparingByValue());
        for (int i = 0; i < 5; i++){
            aux.append("OPTION ").append(list3.get(list3.size() - i - 1).getKey()).append(" - ").append(decimalFormat.format(list3.get(list3.size() - i - 1).getValue())).append(" %\n");

        }

        return aux.toString();
    }

    public String generateScalingOptionsReport() {
        StringBuilder aux = new StringBuilder("");
        int total = 0;
        int sAgree = 0; int agree = 0; int neutral = 0;
        int disagree = 0; int sDisagree = 0;

                List <String> str = scalingOptions.keySet().iterator().next();
                for (int i = 0; i < str.size(); i++) {
                    if (str.get(i).equals("1")) {
                        sAgree++;
                        total++;
                    }
                    if (str.get(i).equals("2")) {
                        agree++;
                        total++;
                    }
                    if (str.get(i).equals("3")) {
                        neutral++;
                        total++;
                    }
                    if (str.get(i).equals("4")) {
                        disagree++;
                        total++;
                    }
                    if (str.get(i).equals("5")) {
                        sDisagree++;
                        total++;
                    }
                }

        aux.append("Strongly Agree").append(" - ").append( decimalFormat.format(sAgree/(float) total * 100)).append(" %\n");
        aux.append("Agree").append(" - ").append( decimalFormat.format(agree/(float) total * 100)).append(" %\n");
        aux.append("Neutral").append(" - ").append(  decimalFormat.format(neutral/(float) total * 100)).append(" %\n");
        aux.append("Disagree").append(" - ").append(  decimalFormat.format(disagree/(float) total * 100)).append(" %\n");
        aux.append("Strongly Disagree").append(" - ").append(  decimalFormat.format(sDisagree/(float) total * 100)).append(" %\n");
        return aux.toString();
    }

    public String generateMultipleChoiceReport() {
        StringBuilder aux = new StringBuilder("");
        int totalMChoice = 0;
        List <String> options = new ArrayList<>();
        for (List <String> set : multipleChoice.keySet()){
            List <List <String>> opt = multipleChoice.get(set);
            for (int i = 0; i < opt.size(); i++) {
                totalMChoice++;
                for (int j = 0; j < opt.get(i).size(); j++) {
                    if (!options.contains(opt.get(i).get(j))){
                        options.add(opt.get(i).get(j));
                    }
                }
            }
        }

        float percent1 = 0, percent2= 0, percent3= 0, percentOther= 0;
        float a = 0; float b= 0;float c= 0;
        boolean boo1 = false;
        for (List<String> set : multipleChoice.keySet()) {
            if (set.contains("1") && set.contains("2")) {
                a++;
            }
            if (set.contains("1") && set.contains("3")) {
                b++;
            }
            if (set.contains("2") && set.contains("3")) {
                c++;
            }
        }
        for (List<String> set : multipleChoice.keySet()) {
            for (int j = 0; j < set.size(); j++) {
                if (set.get(j).contains("1")) {
                    percent1 = percent1 + ((float) multipleChoice.get(set).size());
                    boo1 = true;
                }
            }
        }

        percent1 =( percent1 / (float) totalMChoice ) * 100;
        if (!boo1){
            aux.append("OPTION: ").append("1").append(" - ").append(0).append(" %\n");
        }else {
            aux.append("OPTION: ").append("1").append(" - ").append(decimalFormat.format(percent1)).append(" %\n");
        }

        boolean boo2 = false;
        for (List<String> set : multipleChoice.keySet()) {
            for (int j = 0; j < set.size(); j++) {
                if (set.get(j).contains("2") ) {
                    percent2 = percent2 + ((float) multipleChoice.get(set).size());
                    boo2 = true;
                }
            }
        }
        percent2 =( percent2 / (float) totalMChoice ) * 100;
        if (!boo2){
            aux.append("OPTION: ").append("2").append(" - ").append(0).append(" %\n");
        } else {
            aux.append("OPTION: ").append("2").append(" - ").append(decimalFormat.format(percent2)).append(" %\n");
        }

        boolean boo3 = false;
        for (List<String> set : multipleChoice.keySet()) {
            for (int j = 0; j < set.size(); j++) {
                if (set.get(j).contains("3") ) {
                    percent3 = percent3 + ((float) multipleChoice.get(set).size());
                    boo3 = true;
                }
            }
        }
        percent3 = ( percent3/ totalMChoice) * 100;
        if (!boo3){
            aux.append("OPTION: ").append("3").append(" - ").append(0).append(" %\n");
        } else {
            aux.append("OPTION: ").append("3").append(" - ").append(decimalFormat.format(percent3)).append(" %\n");
        }

        int oof = 0;
        for (List<String> set : multipleChoice.keySet()) {
            for (int j = 0; j < set.size(); j++) {
                if (set.get(j).contains("4") || set.get(j).contains("5") || set.get(j).contains("6")) {
                   oof ++;
                }
            }
        }
        percentOther = ((float) oof / (float) totalMChoice )* 100;
        aux.append("OTHERS" + " - ").append(decimalFormat.format(percentOther)).append(" %\n");
        aux.append("\nCombinations\n");
        aux.append("1 + 2").append(" - ").append(decimalFormat.format( a/(float) totalMChoice * 100)).append(" %\n");
        aux.append("1 + 3").append(" - ").append(decimalFormat.format( b/(float) totalMChoice * 100)).append(" %\n");
        aux.append("2 + 3").append(" - ").append(decimalFormat.format(c/(float) totalMChoice * 100)).append(" %\n");
        return aux.toString();
    }

    public String generateSingleChoiceReport() {
        StringBuilder aux = new StringBuilder("");
        int totalSingleChoice = 0;
        List <String> options = new ArrayList<>();
        for (String set : singleChoice.keySet()){
            List <List <String>> opt = singleChoice.get(set);
            for (int i = 0; i < opt.size(); i++) {
                totalSingleChoice++;
                for (int j = 0; j < opt.get(i).size(); j++) {
                    if (!options.contains(opt.get(i).get(j))){
                        options.add(opt.get(i).get(j));
                    }
                }
            }
        }
        int other = 0;
        boolean boo1 = false; int a = 0;
        boolean boo2 = false; int b = 0;
        boolean boo3 = false; int c = 0;

        for(String set : singleChoice.keySet()) {
            if (set.equals("1")) {
                a++;
                boo1 = true;
            }
        }
        if (boo1){
            float percent = ((float) singleChoice.get("1").size() / (float) totalSingleChoice) * 100;
            aux.append("OPTION: ").append(1).append(" - ").append(decimalFormat.format(percent)).append(" %\n");
        }else {
            aux.append("OPTION: ").append(1).append(" - ").append(0).append(" %\n");
        }

        for(String set : singleChoice.keySet()) {
            if (set.equals("2")) {
                b++;
                boo2 = true;
            }
        }
        if (boo2){
            float percent = ((float) singleChoice.get("2").size() / (float) totalSingleChoice) * 100;
            aux.append("OPTION: ").append(2).append(" - ").append(decimalFormat.format(percent)).append(" %\n");
        } else {
            aux.append("OPTION: ").append(2).append(" - ").append(0).append(" %\n");
        }

        for(String set : singleChoice.keySet()) {
            if (set.equals("3")) {
                c++;
                boo3 = true;
            }
        }
        if (boo3){
            float percent = ((float) singleChoice.get("3").size() / (float) totalSingleChoice) * 100;
            aux.append("OPTION: ").append(3).append(" - ").append(decimalFormat.format(percent)).append(" %\n");
        }else {
            aux.append("OPTION: ").append(3).append(" - ").append(0).append(" %\n");
        }

        for(String set : singleChoice.keySet()) {
            if (!set.equals("1") && !set.equals("2") && !set.equals("3")){
            other = other + singleChoice.get(set).size();
        }
        }

        float percent2 = ((float) other / (float) totalSingleChoice )* 100;
        aux.append("OTHERS" + " - ").append(decimalFormat.format(percent2)).append(" %\n");
        return aux.toString();
    }

}

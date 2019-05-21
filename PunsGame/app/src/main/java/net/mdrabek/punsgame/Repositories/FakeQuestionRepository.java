package net.mdrabek.punsgame.Repositories;

import net.mdrabek.punsgame.Models.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeQuestionRepository implements QuestionRepository
{
    private Map<Question.QuestionCategory, ArrayList<Question>> questions;

    public FakeQuestionRepository()
    {
        populateQuestionMap();
    }

    private void populateQuestionMap()
    {
        if(questions != null)
            questions.clear();
        else
            questions = new HashMap<>();

        Question[] foodQuestions = new Question[]
                {
                        new Question(Question.QuestionCategory.FOOD, "Pitahaja"),
                        new Question(Question.QuestionCategory.FOOD, "Zielone jabłko"),
                        new Question(Question.QuestionCategory.FOOD, "Liczi"),
                        new Question(Question.QuestionCategory.FOOD, "Zielony banan"),
                        new Question(Question.QuestionCategory.FOOD, "Soczysta pomarańcza"),
                        new Question(Question.QuestionCategory.FOOD, "Granat"),
                        new Question(Question.QuestionCategory.FOOD, "Borówka amerykańska"),
                        new Question(Question.QuestionCategory.FOOD, "Czerwona porzeczka"),
                        new Question(Question.QuestionCategory.FOOD, "Papaja hawajska"),
                        new Question(Question.QuestionCategory.FOOD, "Fioletowa marchew"),
                        new Question(Question.QuestionCategory.FOOD, "Mini arbuz"),
                        new Question(Question.QuestionCategory.FOOD, "Żółta malina"),
                        new Question(Question.QuestionCategory.FOOD, "Agrest"),
                        new Question(Question.QuestionCategory.FOOD, "Pigwa"),
                        new Question(Question.QuestionCategory.FOOD, "Aronia"),
                        new Question(Question.QuestionCategory.FOOD, "Bakłażan"),
                        new Question(Question.QuestionCategory.FOOD, "Ogórek szklarniowy"),
                        new Question(Question.QuestionCategory.FOOD, "Żółta papryka"),
                        new Question(Question.QuestionCategory.FOOD, "Topinambur"),
                        new Question(Question.QuestionCategory.FOOD, "Imbir"),
                        new Question(Question.QuestionCategory.FOOD, "Marakuja")
                };

        questions.put(Question.QuestionCategory.FOOD, new ArrayList<>(Arrays.asList(foodQuestions)));

        Question[] animalsQuestions = new Question[]
                {
                        new Question(Question.QuestionCategory.ANIMALS, "Owczarek niemiecki"),
                        new Question(Question.QuestionCategory.ANIMALS, "Labrador"),
                        new Question(Question.QuestionCategory.ANIMALS, "Słoń indyjski"),
                        new Question(Question.QuestionCategory.ANIMALS, "Niedźwiedź polarny"),
                        new Question(Question.QuestionCategory.ANIMALS, "Motyl monarcha"),
                        new Question(Question.QuestionCategory.ANIMALS, "Lew morski"),
                        new Question(Question.QuestionCategory.ANIMALS, "Pingwin Magellana"),
                        new Question(Question.QuestionCategory.ANIMALS, "Panda wielka"),
                        new Question(Question.QuestionCategory.ANIMALS, "Wombat australijski"),
                        new Question(Question.QuestionCategory.ANIMALS, "Wąż Eskulapa"),
                        new Question(Question.QuestionCategory.ANIMALS, "Suseł"),
                        new Question(Question.QuestionCategory.ANIMALS, "Żbik"),
                        new Question(Question.QuestionCategory.ANIMALS, "Sokół wędrowny"),
                        new Question(Question.QuestionCategory.ANIMALS, "Rybitwa"),
                        new Question(Question.QuestionCategory.ANIMALS, "Zebra"),
                        new Question(Question.QuestionCategory.ANIMALS, "Hipopotam"),
                        new Question(Question.QuestionCategory.ANIMALS, "Żyrafa"),
                        new Question(Question.QuestionCategory.ANIMALS, "Gepard"),
                        new Question(Question.QuestionCategory.ANIMALS, "Gazela"),
                        new Question(Question.QuestionCategory.ANIMALS, "Chihuahua")
                };

        questions.put(Question.QuestionCategory.ANIMALS, new ArrayList<>(Arrays.asList(animalsQuestions)));

        Question[] fictionCharactersQuestions = new Question[]
                {
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Harry Potter"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Newton Skamander"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Tony Stark"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Johnny Bravo"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Trevor Philips"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Frank Underwood"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Vito Corleone"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Papa smerf"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Anakin Skywalker"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Yoda"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Jack Sparrow"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Indiana Jones"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Han Solo"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Bruce Wayne"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Bolek i Lolek"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Pszczółka Maja"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Koziołek Matołek"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Kot w butach"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Ash Ketchum"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Kaczor Donald"),
                        new Question(Question.QuestionCategory.FICTION_CHARACTERS, "Myszka Miki")
                };

        questions.put(Question.QuestionCategory.FICTION_CHARACTERS, new ArrayList<>(Arrays.asList(fictionCharactersQuestions)));

        Question[] adagesQuestions = new Question[]
                {
                        new Question(Question.QuestionCategory.ADAGES, "Fortuna kołem się toczy"),
                        new Question(Question.QuestionCategory.ADAGES, "Co ma wisieć, nie utonie"),
                        new Question(Question.QuestionCategory.ADAGES, "Dzieci i ryby głosu nie mają"),
                        new Question(Question.QuestionCategory.ADAGES, "Mądry Polak po szkodzie"),
                        new Question(Question.QuestionCategory.ADAGES, "Tonący brzytwy się chwyta"),
                        new Question(Question.QuestionCategory.ADAGES, "Kto pierwszy ten lepszy"),
                        new Question(Question.QuestionCategory.ADAGES, "Z deszczu pod rynnę"),
                        new Question(Question.QuestionCategory.ADAGES, "Pod latarnią najciemniej"),
                        new Question(Question.QuestionCategory.ADAGES, "Pyrrusowe zwycięstwo"),
                        new Question(Question.QuestionCategory.ADAGES, "Strach ma wielkie oczy"),
                        new Question(Question.QuestionCategory.ADAGES, "Nieszczęścia chodzą parami"),
                        new Question(Question.QuestionCategory.ADAGES, "Syzyfowa praca"),
                        new Question(Question.QuestionCategory.ADAGES, "Nadzieja matką głupich"),
                        new Question(Question.QuestionCategory.ADAGES, "Głodnemu chleb na myśli"),
                        new Question(Question.QuestionCategory.ADAGES, "Kłamstwo ma krótkie nogi"),
                        new Question(Question.QuestionCategory.ADAGES, "Chłop jak dąb"),
                        new Question(Question.QuestionCategory.ADAGES, "Dać komuś kosza"),
                        new Question(Question.QuestionCategory.ADAGES, "Grać w otwarte karty"),
                        new Question(Question.QuestionCategory.ADAGES, "Pięta Achillesowa"),
                        new Question(Question.QuestionCategory.ADAGES, "Koń trojański"),
                        new Question(Question.QuestionCategory.ADAGES, "Gruszki na wierzbie")
                };

        questions.put(Question.QuestionCategory.ADAGES, new ArrayList<>(Arrays.asList(adagesQuestions)));
    }

    @Override
    public ArrayList<Question> getQuestionList(Question.QuestionCategory category)
    {
        if(!questions.containsKey(category))
            return null;

        return questions.get(category);
    }

    @Override
    public ArrayList<Question> getQuestionList()
    {
        ArrayList<Question> fullQuestionList = questions.values().stream().collect(ArrayList::new, List::addAll, List::addAll);
        return fullQuestionList;
    }

    @Override
    public ArrayList<Question.QuestionCategory> getCategories()
    {
        ArrayList<Question.QuestionCategory> categories = new ArrayList<>();
        categories.addAll(questions.keySet());
        return categories;
    }
}

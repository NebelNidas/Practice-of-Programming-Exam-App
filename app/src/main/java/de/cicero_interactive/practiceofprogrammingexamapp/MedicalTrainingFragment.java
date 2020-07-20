
package de.cicero_interactive.practiceofprogrammingexamapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class MedicalTrainingFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MedicalTrainingAdapter adapter;

    private MyDatabase myDatabase;
    private ArrayList<Article> articles;
    private RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inf = inflater.inflate(R.layout.fragment_medical_training, container, false);

        getActivity().setTitle(R.string.medical_training);

        requestQueue = Volley.newRequestQueue(getActivity());
        setHasOptionsMenu(true);
        jsonParse();

        recyclerView = inf.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new MedicalTrainingAdapter(getActivity(), articles);

        buildRecyclerView();
        return inf;
    }

    public  void jsonParse() {
        // It should work, but for some reason I don't get back any response from the JsonObjectRequest! >:(
        // So, for, now, I'm providing the array myself...
        // TODO: Fix this madness!
        /*
        final Gson gson = new Gson();
        String url = "https://pastebin.com/raw/1ENW5805";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("test", "url");
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Type type = new TypeToken<ArrayList<Article>>(){}.getType();
                        articles = gson.fromJson(jsonArray.getJSONObject(i).toString(), type);
                        buildRecyclerView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        */
        articles = new ArrayList<Article>() {{
            add(new Article("Should you worry about too high blood sugar levels?",
                    new Date(-1900 + 2020, -1 + 07, 17),
                    "Julian Burner",
                    "Many people have high blood glucose levels. But where do you draw the line after which it becomes dangerous? We will analyze this topic in today's article.",
                    "The blood sugar level, blood sugar concentration, or blood glucose level is the concentration of glucose present in the blood of humans and other animals. Glucose is a simple sugar and approximately 4 grams of glucose are present in the blood of a 70-kilogram (150 lb) human at all times. The body tightly regulates blood glucose levels as a part of metabolic homeostasis. Glucose is stored in skeletal muscle and liver cells in the form of glycogen; in fasted individuals, blood glucose is maintained at a constant level at the expense of glycogen stores in the liver and skeletal muscle.\n\nIn humans, a blood glucose level of four grams, or about a teaspoon, is critical for normal function in a number of tissues, and the human brain consumes approximately 60% of blood glucose in fasted, sedentary individuals. A persistent elevation in blood glucose leads to glucose toxicity, which contributes to cell dysfunction and the pathology grouped together as complications of diabetes. Glucose can be transported from the intestines or liver to other tissues in the body via the bloodstream. Cellular glucose uptake is primarily regulated by insulin, a hormone produced in the pancreas.\n\nGlucose levels are usually lowest in the morning, before the first meal of the day, and rise after meals for an hour or two by a few millimoles. Blood sugar levels outside the normal range may be an indicator of a medical condition. A persistently high level is referred to as hyperglycemia; low levels are referred to as hypoglycemia. Diabetes mellitus is characterized by persistent hyperglycemia from any of several causes, and is the most prominent disease related to failure of blood sugar regulation. There are different methods of testing and measuring blood sugar levels.\n\nThe intake of alcohol causes an initial surge in blood sugar, and later tends to cause levels to fall. Also, certain drugs can increase or decrease glucose levels.\n\nIf blood sugar levels remain too high the body suppresses appetite over the short term. Long-term hyperglycemia causes many health problems including heart disease, cancer, eye, kidney, and nerve damage.\n\nBlood sugar levels above 16.7 mmol/L (300mg/dL) can cause fatal reactions. Ketones will be very high (a magnitude higher than when eating a very low carbohydrate diet) initiating ketoacidosis. The Mayo Clinic recommends emergency room treatment above 16.7 mmol/L (300 mg/dL) blood glucose.\n\nThe most common cause of hyperglycemia is diabetes. When diabetes is the cause, physicians typically recommend an anti-diabetic medication as treatment. From the perspective of the majority of patients, treatment with an old, well-understood diabetes drug such as metformin will be the safest, most effective, least expensive, and most comfortable route to managing the condition. Diet changes and exercise implementation may also be part of a treatment plan for diabetes.\n\n\nCopyright © Wikipedia"
            ) {{
                setQuiz(new ArticleQuiz("Above which level does bood sugar become dangerous?",
                        "A value of 50 mg/dL is dangerously low, not high. 300 mg/dL is the correct answer, if it stays at this level or gets even higher it could be a medical emergency and you should get to a hospital immediatly. 600 mg/dL already are lethal - 2000 mg/dL would instantly kill you.",
                        new ArrayList<ArticleQuizAnswer>() {{
                            add(new ArticleQuizAnswer("50 mg/dL", false));
                            add(new ArticleQuizAnswer("300 mg/dL", true));
                            add(new ArticleQuizAnswer("600 mg/dL", false));
                            add(new ArticleQuizAnswer("2000 mg/dL", false));
                        }}
                ));
            }});
            add(new Article("First Aid: The basics",
                    new Date(-1900 + 2020, -1 + 06, 30),
                    "Max Mustermann",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et.",
                    "The first step in any emergency is the recognition of the problem and providing help. When in doubt or when someone is seriously injured or ill, you should always activate the emergency response system by calling 911. If you’re not sure how serious the situation is, the 911 operator will ask you a series of questions to determine the seriousness of it.\n\n" +
                            "Remain on the line until additional help arrives, or until the 911 operator tells you to hang up. Emergency system dispatchers can guide you through the steps of performing cardiopulmonary resuscitation (CPR), using an automatic external defibrillator (AED), or delivering basic care until additional help arrives.\n\n" +
                            "Whether you are at home, work, or school, know where the first aid kit and the AED are kept and be familiar with their contents. Know how to activate the emergency response system (by calling 911 if in the United States). Be aware of any policies in the workplace regarding medical emergencies.\n\n" +
                            "After determining the problem, the next step in providing help is to determine the unresponsiveness of the injured or ill person. The best way to determine this is to tap the person and talk loudly to them: “Are you okay?” After determining unresponsiveness, yell for help. Look for any medical identifications, such as a necklace or a bracelet. This may provide a valuable clue to the cause of the situation. danger when going to the assistance of another person.\n\n\n" +
                            "Response – is the person conscious? Do they respond when you talk to them, touch their hands or squeeze their shoulder?\n\n" +
                            "Send for help – call triple zero (000). Don’t forget to answer the questions asked by the operator.\n\n" +
                            "Airway – Is the person’s airway clear? Is the person breathing?\n\n" +
                            "If the person is responding, they are conscious and their airway is clear, assess how you can help them with any injury.\n\n" +
                            "If the person is not responding and they are unconscious, you need to check their airway by opening their mouth and having a look inside. If their mouth is clear, tilt their head gently back (by lifting their chin) and check for breathing. If the mouth is not clear, place the person on their side, open their mouth and clear the contents, then tilt the head back and check for breathing.\n\n" +
                            "Breathing – check for breathing by looking for chest movements (up and down). Listen by putting your ear near to their mouth and nose. Feel for breathing by putting your hand on the lower part of their chest. If the person is unconscious but breathing, turn them onto their side, carefully ensuring that you keep their head, neck and spine in alignment. Monitor their breathing until you hand over to the ambulance officers.\n\n" +
                            "CPR (cardiopulmonary resuscitation) – if an adult is unconscious and not breathing, make sure they are flat on their back and then place the heel of one hand in the centre of their chest and your other hand on top. Press down firmly and smoothly (compressing to one third of their chest depth) 30 times. Give two breaths. To get the breath in, tilt their head back gently by lifting their chin. Pinch their nostrils closed, place your open mouth firmly over their open mouth and blow firmly into their mouth. Keep going with the 30 compressions and two breaths at the speed of approximately five repeats in two minutes until you hand over to the ambulance officers or another trained person, or until the person you are resuscitating responds. The method for CPR for children under eight and babies is very similar and you can learn these skills in a CPR course.\n\n" +
                            "Defibrillator – for unconscious adults who are not breathing, apply an automated external defibrillator (AED) if one is available. They are available in many public places, clubs and organisations. An AED is a machine that delivers an electrical shock to cancel any irregular heart beat (arrhythmia), in an effort get the normal heart beating to re-establish itself. The devices are very simple to operate. Just follow the instructions and pictures on the machine, and on the package of the pads, as well as the voice prompts. If the person responds to defibrillation, turn them onto their side and tilt their head to maintain their airway. Some AEDs may not be suitable for children.\n" +
                            "\n\n\nCopyright © nhcps.com & betterhealth.vic.gov.au"
            ) {{
                setQuiz(new ArticleQuiz("Which is the correct sequence of action when you find someone unconscious?",
                        "This sequence is called DRSABCD and should be remembered. It's very important to take the right steps at the right time, as this could mean the difference between life and death for the patient.",
                        new ArrayList<ArticleQuizAnswer>() {{
                            add(new ArticleQuizAnswer("Danger - Response - Send for help - Airway - Breathing - CPR - Defibrillator ", true));
                            add(new ArticleQuizAnswer("Send for help - Response - Breathing - Danger - Defibrillator - Airway - CPR", false));
                            add(new ArticleQuizAnswer("Danger - Send for help - Airway - Response - Breathing - CPR - Defibrillator", false));
                            add(new ArticleQuizAnswer("Response - Airway - Send for help - CPR - Defibrillator - Breathing - Danger", false));
                        }}
                ));
            }});
            add(new Article("How to analyze your blood results correctly",
                    new Date(-1900 + 2020, -1 + 07, 9),
                    "Max Mustermann",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr.",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.\n\n At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \nLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.\n\n Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \nDuis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi."
            ));
            add(new Article("How to correctly draw blood from your veins",
                    new Date(-1900 + 2020, -1 + 06, 30),
                    "Julian Burner",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et.",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.\n\n At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \nLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.\n\n Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \nDuis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi."
            ));
        }};
    }

    public void buildRecyclerView() {
        // Setting hasFixedSize to true will increase performance
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MedicalTrainingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), MedicalTrainingArticleViewerActivity.class);
                intent.putExtra("article", articles.get(position));
                startActivity(intent);
            }
        });
    }

    // Filtering
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater = inflater;
        menuInflater.inflate(R.menu.medical_training_menu, menu);

        MenuItem search_item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search_item.getActionView();

        searchView.setImeOptions((EditorInfo.IME_ACTION_DONE));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
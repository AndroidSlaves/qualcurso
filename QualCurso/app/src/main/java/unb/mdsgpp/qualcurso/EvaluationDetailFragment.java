/*****************************
 * Class name: EvaluationDetailFragment (.java)
 * Purpose: Class that shows a fragment.
 *****************************/

package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.ArrayList;
import java.util.HashMap;

import models.Article;
import models.Bean;
import models.Book;
import models.Course;
import models.Evaluation;
import models.Institution;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class EvaluationDetailFragment extends Fragment{

	// Represents the unic identification number field of the course.
	private static final String ID_COURSE = "idCourse";

	// Represents the field of the institution in which this course is tought.
	private static final String ID_INSTITUTION = "idInstitution";

	// Represents field year of the evaluated institution.
	private static final String YEAR = "year";

	// Object that connects with the database.
	BeanListCallbacks beanCallbacks;

	// Method that instatiate the evaluationDetailFragment, an contructor.
	public EvaluationDetailFragment() {
		super();

		//Represents the object that will initiate the argument with the evaluation fileds.
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, 0);
		args.putInt(ID_INSTITUTION, 0);
		args.putInt(YEAR, 0);
		this.setArguments(args);
	}

	/**
	 * Creates a new instance of the EvaluationDetailFragmentObject.
	 *
	 * @param id_institution
	 * 				Object id_institution.
	 * @param id_course
	 * 				Object id_course.
	 * @param year
	 * 				Object year.
	 *
	 * @return
	 * 				A new fragment with basic informations.
	 *
	 */
	public static EvaluationDetailFragment newInstance(int id_institution, int id_course,int year){
		assert (id_institution >= 0) : "idInstitution must never be negative";
		assert (id_course >= 0) : "idCourse must never be negative";
		assert (year > 1990) : "year must never be smaller 1990";

		// Object to receive the initial value.
		EvaluationDetailFragment fragment = new EvaluationDetailFragment();

		// Represents the object that will initiate the argument with the evaluation fileds.
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, id_course);
		args.putInt(ID_INSTITUTION, id_institution);
		args.putInt(YEAR, year);
		fragment.setArguments(args);
		return fragment;
	}

	// Android default method  for fragment.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// View containing the main screen to be shown to the user.
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

		// Area of text to be filled with the acronym of the institution.
		TextView textView1 = (TextView) rootView.findViewById(R.id.university_acronym);
		textView1.setText(Institution.get(getArguments().getInt(ID_INSTITUTION)).getAcronym());

		// Information of the institution that was evaluated.
		Evaluation evaluation = Evaluation.getFromRelation(getArguments().getInt(ID_INSTITUTION), 
				getArguments().getInt(ID_COURSE), getArguments().getInt(YEAR));

		// Area of text that will be filled with all the evaluation information.
		TextView textView2 = (TextView) rootView.findViewById(R.id.general_data);
		textView2.setText(getString(R.string.evaluation_date)+": " + evaluation.getYear() +
				          "\n"+getString(R.string.course)+": " + Course.get(getArguments().getInt(ID_COURSE)).getName()
				          + "\n"+getString(R.string.modality)+": " + evaluation.getModality());

		// List that indicates the institution evaluation.
		ListView indicatorList = (ListView) rootView.findViewById(R.id.indicator_list);
		indicatorList.setAdapter(new IndicatorListAdapter(getActivity().getApplicationContext(),
				                 R.layout.evaluation_list_item, getListItems(evaluation)));
		
		return rootView;
	}
	
	public ArrayList<HashMap<String, String>> getListItems(Evaluation evaluation){

		assert(evaluation != null) : "evaluation must never be null";
		assert(evaluation.getIdArticles() >= 0) : "evaluation's idArticles must never be negative";
		assert(evaluation.getIdBooks() >= 0) : "evaluation's idBooks must never be negative";

		// List of Hashes list of strings with the information.
		ArrayList<HashMap<String, String>> hashList = new ArrayList<HashMap<String,String>>();

		// List of indicator calculated from the evaluations.
		ArrayList<Indicator> indicators = Indicator.getIndicators();

		// Book object with the books id present in the evaluation.
		Book book = Book.get(evaluation.getIdBooks());

		// Book object with the books id present in the evaluation.
		Article article = Article.get(evaluation.getIdArticles());

		// Generic object that connects to the database.
		Bean bean = null;
		for(Indicator i : indicators){

			//Hash map containing all assets from the evaluated intitution, like books and articles.
			HashMap<String, String> hashMap = new HashMap<String, String>();
			if(evaluation.fieldsList().contains(i.getValue())){
				bean = evaluation;
			}else if(book.fieldsList().contains(i.getValue())){
				bean = book;
			}else if(article.fieldsList().contains(i.getValue())) {
				bean = article;
			}
			if(bean!=null){
				hashMap.put(IndicatorListAdapter.INDICATOR_VALUE, i.getValue());
				hashMap.put(IndicatorListAdapter.VALUE, bean.get(i.getValue()));
				hashList.add(hashMap);
			}
		}
		return hashList;
	}
	
	@Override
	public void onAttach(Activity activity) {
		assert (activity != null) : "activity must never be null";
		
		super.onAttach(activity);
		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}
	
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }


}

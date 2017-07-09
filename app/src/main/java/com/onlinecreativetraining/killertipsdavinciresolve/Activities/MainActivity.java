package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Constants;
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.util.SignupDialog;

public class MainActivity extends Activity implements View.OnClickListener {

    SharedPreferences pref;
    PopupWindow popup;
    AlertDialog NewsletterWin;

    public static String email;
    public static String password;

    public static FragmentManager fm;
    public static FragmentTransaction fragmentTransaction;

    public static ImageView imgBack,logo_img;
    public static ImageView imgMenu,menu_img;
    public static LinearLayout searchView;
    public static LinearLayout footer;
    public static ProgressBar progressBar;
    public static TextView txtBack;
    public static TextView txtNext;
    public static TextView txtTitle;
    EditText edtSearch;
    TextView txtWWW;
    TextView txtSHARE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup layout = (ViewGroup) findViewById(android.R.id.content).getRootView();
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout rl = new RelativeLayout(this);
        rl.setGravity(Gravity.CENTER);
        rl.addView(progressBar);
        layout.addView(rl, params);

        footer = (LinearLayout) findViewById(R.id.footer);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        logo_img = (ImageView) findViewById(R.id.imageView);
        menu_img = (ImageView) findViewById(R.id.menu_img);
        searchView = (LinearLayout) findViewById(R.id.searchView);
        txtBack = (TextView) findViewById(R.id.txtBack);
        txtNext = (TextView) findViewById(R.id.txtNext);
        txtWWW = (TextView) findViewById(R.id.txt_www);
        txtSHARE = (TextView) findViewById(R.id.txt_share);
        edtSearch = (EditText) findViewById(R.id.edit_search);
        txtTitle = (TextView) findViewById(R.id.txtTitle);

        imgBack.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        txtWWW.setOnClickListener(this);
        txtSHARE.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        txtNext.setOnClickListener(this);

        Typeface custom_font1 = Typeface.createFromAsset(this.getAssets(), "fonts/Futura.ttf");
        if (custom_font1 != null)
            Log.d("status", "success");
        txtWWW.setTypeface(custom_font1);
        txtSHARE.setTypeface(custom_font1);

        edtSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (edtSearch.getText().toString().equals(""))
                        return false;
                    addSearchFragment(edtSearch.getText().toString());
                }
                return false;
            }
        });

        pref = getSharedPreferences("killerTip", 0);
        fm = getFragmentManager();
        Fragment frag;
        init();
        if (CheckLogin() == 0) {

            logo_img.getLayoutParams().height = 400;
            logo_img.getLayoutParams().width = 600;
            logo_img.setScaleType(ImageView.ScaleType.FIT_XY);
            frag = new LoginFragment();

        } else {
            frag = new MainFragment();
            imgMenu.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);
            footer.setVisibility(View.VISIBLE);
        }
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.grid_fragment, frag).commit();
    }

    void init() {
        footer.setVisibility(View.INVISIBLE);
        imgMenu.setVisibility(View.INVISIBLE);
        imgBack.setVisibility(View.INVISIBLE);
        searchView.setVisibility(View.INVISIBLE);
        logo_img.getLayoutParams().height = 400;
        logo_img.getLayoutParams().width = 600;
        logo_img.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public static void openFragment(Fragment frag) {
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.grid_fragment, frag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        imgBack.setVisibility(View.VISIBLE);
    }

    public static void openTipsFragment(Fragment frag) {
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.grid_fragment, frag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack("tips");
        fragmentTransaction.commit();
        imgBack.setVisibility(View.VISIBLE);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                if (fm.getBackStackEntryCount() > 0) {
                    Log.e("MainActivity", "popping backstack");
                    fm.popBackStack();
                    logo_img.getLayoutParams().height = 200;
                    logo_img.getLayoutParams().width = 300;
                    logo_img.setScaleType(ImageView.ScaleType.FIT_XY);

                    if (fm.getBackStackEntryCount() == 1) {
                        imgBack.setVisibility(View.INVISIBLE);
                    }
                }
                if (Constants.detailwindowflag == true) {
                    Constants.detailwindowflag = false;
                    MainActivity.txtBack.setText("     ");
                    MainActivity.txtNext.setText("     ");
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                if (imm.isActive())
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                break;
            case R.id.imgMenu:
                showMenu(v);
                break;
            case R.id.txt_www:
                openWebView();
                break;
            case R.id.txt_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case R.id.txtBack:
                if (txtBack.getText().equals("")) {
                    return;
                }
                int index = Constants.getTipIndex();
                if (index > 0) {
                    Constants.setTipIndex(index - 1);
                } else {
                    Constants.setTipIndex(Constants.getTipData().size() - 1);
                }
                popFragment();
                openTipsFragment(new TipDetailFragment());
                break;
            case R.id.txtNext:
                if (txtNext.getText().equals("")) return;
                index = Constants.getTipIndex();
                if (index < Constants.getTipData().size() - 1) {
                    Constants.setTipIndex(index + 1);
                } else {
                    Constants.setTipIndex(0);
                }
                popFragment();
                openTipsFragment(new TipDetailFragment());
                break;
            default:
                break;
        }
    }

    int CheckLogin() {
        String token = pref.getString("token", null);
        if (token == null) {
            return 0;
        } else {
            Constants.token = token;
            return 1;
        }
    }
    public void displayPopupWindow(View anchorView) {
        if (popup == null)
            popup = new PopupWindow(getApplicationContext());
        View layout = getLayoutInflater().inflate(R.layout.popup_layout, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        final TextView tvContactus = (TextView) layout.findViewById(R.id.menuNewsletter);
        final TextView tvLogout = (TextView) layout.findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvContactus.setBackgroundColor(0xdcdcdc);
                tvLogout.setBackgroundColor(0xFFB1AFAF);
                SharedPreferences.Editor editor = pref.edit();
                Constants.token = "";
                editor.clear();
                editor.commit();
                init();

                Fragment frag = new LoginFragment();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.grid_fragment, frag);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
                popup.dismiss();
            }
        });
        tvContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tvLogout.setBackgroundColor(0xdcdcdc);
                tvContactus.setBackgroundColor(0xFFB1AFAF);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.ACTION_ASSIST, "xRanky Android app");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject");
                i.putExtra(Intent.EXTRA_TEXT, "message");

                try {
                    startActivity(Intent.createChooser(i, "Contact  us"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                popup.dismiss();
            }
        });
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);
    }

    void showMenu(final View anchorView) {
        if (popup != null) {
            popup.showAsDropDown(anchorView);
            return;
        }
        View view = getLayoutInflater().inflate(R.layout.popup_menu, null);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        String[] menuItems = {
                "Home", "Purchase More Tips", "Show All Tips", "Show Favourites", "Show Purchased Tips", "Signup For Our Newsletter", "Credits"
                , "Terms of Use", "Privacy Policy", "Refund Policy", "Signout"
        };
        ListAdapter adapter = new ListAdapter(MainActivity.this, menuItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestParams params = new RequestParams();
                switch (position) {
                    case 0:
                        popAllFragment();
                        openFragment(new MainFragment());
                        break;
                    case 1:
                        popAllFragment();
                        openFragment(new UnPurchaseMainFragment());
                        break;
                    case 2:
                        params.put("token", Constants.token);
                        Constants.setParams(params);
                        openFragment(new TipsFragment());
                        break;
                    case 3:
                        params.put("token", Constants.token);
                        params.put("favourite", true);
                        Constants.setParams(params);
                        openFragment(new TipsFragment());
                        break;
                    case 4:
                        params.put("token", Constants.token);
                        Constants.setParams(params);
                        openFragment(new TipsFragment());
                        break;
                    case 5:
                        openNewsletter(anchorView);
                        break;
                    case 6:
                        openFragment(new CreditsFragment());
                        break;
                    case 7:
                        openFragment(TermsofUseFragment.newInstance(1));
                        break;
                    case 8:
                        openFragment(TermsofUseFragment.newInstance(2));
                        break;

                    case 9:
                        openFragment(TermsofUseFragment.newInstance(3));
                        break;
                    case 10:
                        init();
                        SharedPreferences.Editor editor = pref.edit();
                        Constants.token = "";
                        editor.clear();
                        editor.commit();
                        popAllFragment();
                        Fragment frag = new LoginFragment();
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.grid_fragment, frag);
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction.commit();
                        break;
                    default:
                        break;
                }
                popup.dismiss();
            }
        });
        popup = new PopupWindow(getApplicationContext());
        popup.setContentView(view);
        popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);
    }

    void openWebView() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://onlinecreativetraining.com"));
        try {
            startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void openNewsletter(View anchorView) {
        SignupDialog dialog = new SignupDialog(this);
        dialog.setOwnerActivity(this);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
//        if (fm.getBackStackEntryCount() == 1) {
//            imgBack.setVisibility(View.INVISIBLE);
//        }
//        super.onBackPressed();
//        setNextBackButtonText();
    }

    public void popFragment()
    {
        if (fm.getBackStackEntryCount() > 0) {
            Log.e("MainActivity", "popping backstack");
            fm.popBackStack();
            if (fm.getBackStackEntryCount() == 1) {
                imgBack.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void popAllFragment(){
        fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private boolean getCurrentFragmentTagNull() {
        int count = fm.getBackStackEntryCount();
        if (count <= 0) {
            return false;
        }

        String fragmentTag = fm.getBackStackEntryAt(count - 1).getName();
        if (fragmentTag == null)
            return true;
        else
            return fragmentTag.equals(null);
    }

    public void setNextBackButtonText(){
        if (getCurrentFragmentTagNull()) {
            txtBack.setText("");
            txtNext.setText("");
        }else {
            txtBack.setText("<");
            txtNext.setText(">");
        }
    }

    public void addSearchFragment(String searchkey){
        SearchResultFragment frag = SearchResultFragment.newInstance(searchkey);
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.grid_fragment, frag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        imgBack.setVisibility(View.VISIBLE);
    }
}

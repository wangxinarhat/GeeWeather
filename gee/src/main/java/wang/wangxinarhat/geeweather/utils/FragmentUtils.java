package wang.wangxinarhat.geeweather.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * Created by wang on 2015/12/15.
 * <p/>
 * Fragmentg管理类
 * <p/>
 * 每次添加Fragment，先findFragmentByTag，如果找到了fragment.isAdded()，就return，如果Fragment没在栈中，Fragment Add
 */
public class FragmentUtils {


//    public static void addFragment(FragmentManager fm, int resId, Fragment from, String toFragmentTag, Bundle args) {
//
//        Fragment to = fm.findFragmentByTag(toFragmentTag);
//
//
//        FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(
//                R.anim.fragment_slide_in_left, R.anim.fragment_slide_out_right);
//
//        if (to == null) {
//            to = FragmentFactory.createFragment(toFragmentTag);
//        }
//
//        if (from != null) {
//
//
//            if (!to.isAdded()) {    // 先判断是否被add过
//
//                if (args != null && !args.isEmpty()) {
//                    to.setArguments(args);
//                }
//
//
//                transaction.hide(from).add(resId, to, toFragmentTag); // 隐藏当前的fragment，add下一个到Activity中
//
//
//                transaction.addToBackStack(null);
//
//            } else {
//                transaction.hide(from).show(to); // 隐藏当前的fragment，显示下一个
//            }
//
//
//        } else {
//            if (!to.isAdded()) {    // 先判断是否被add过
//
//                if (args != null && !args.isEmpty()) {
//                    to.setArguments(args);
//                }
//                transaction.add(resId, to, toFragmentTag); // 隐藏当前的fragment，add下一个到Activity中
//
//                transaction.addToBackStack(null);
//            } else {
//                transaction.show(to); // 隐藏当前的fragment，显示下一个
//            }
//
//
//        }
//
//        transaction.commitAllowingStateLoss();
//
//
//        LogUtils.e("堆栈数量switchFragment : " + fm.getBackStackEntryCount());
//
//
//    }
//
//    public static void replaceFragment(FragmentManager fm, int resId, Fragment from, String toFragmentTag, Bundle args) {
//
//
//        Fragment to = fm.findFragmentByTag(toFragmentTag);
//
//        if (to == null) {
//            to = FragmentFactory.createFragment(toFragmentTag);
//        }
//
//        if (args != null && !args.isEmpty()) {
//            to.setArguments(args);
//        }
//
//        FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(
//                R.anim.fragment_slide_in_left, R.anim.fragment_slide_out_right);
//
//        transaction.replace(resId, to, toFragmentTag);
//
//        transaction.commit();
//
//
//    }


//    public static void turnToFragmentAddStack(FragmentManager fm, Class<? extends Fragment> fragmentClass, String tag, Bundle args, int resId) {
//
//        Fragment fragment = FragmentFactory.createFragment(tag);
//        fragment.setArguments(new Bundle());
//
//
//        if (args != null && !args.isEmpty()) {
//            fragment.getArguments().putAll(args);
//        }
//
//
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
//                android.R.anim.fade_in, android.R.anim.fade_out);
//
//        if (!fragment.isAdded()) {    // 先判断是否被add过
//            ft.add(resId, fragment, tag); // 隐藏当前的fragment，add下一个到Activity中
//        } else {
//            ft.show(fragment); // 隐藏当前的fragment，显示下一个
//        }
//
//
//        ft.addToBackStack(tag);
////        ft.commitAllowingStateLoss();
//        ft.commit();//退出activity时，防止提交后的状态丢失
//    }
//
//    public static void turnToFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass, String tag, Bundle args, int resId) {
//
//
//        //        if (to == null) {
////            try {
////                to = fragmentClass.newInstance();
////                to.setArguments(new Bundle());
////            } catch (java.lang.InstantiationException e) {
////                e.printStackTrace();
////            } catch (IllegalAccessException e) {
////                e.printStackTrace();
////            }
////        }
//
//
//        Fragment fragment = FragmentFactory.createFragment(tag);
//        fragment.setArguments(new Bundle());
//
//
//        if (args != null && !args.isEmpty()) {
//            fragment.getArguments().putAll(args);
//        }
//
//
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
//                android.R.anim.fade_in, android.R.anim.fade_out);
//
//
//        if (!fragment.isAdded()) {
//            ft.add(resId, fragment, tag);
//        } else {
//            ft.show(fragment);
//        }
//
//
//        ft.commit();
//    }


    public static void replaceFragment(FragmentManager manager, Class<? extends Fragment> fragmentClass, boolean isAddToBackStack) {

        Fragment fragment = manager.findFragmentByTag(fragmentClass.getSimpleName());

        if (null == fragment) {
            try {


                fragment = fragmentClass.newInstance();


            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }


        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        if (!fragment.isAdded()) {
            ft.replace(android.R.id.content, fragment, fragment.getClass().getSimpleName());
            if (isAddToBackStack) {
                ft.addToBackStack(null);
            }
        }
        ft.commit();
//        FragmentTransaction ft2 = manager.beginTransaction();
//        if (manager.getFragments() != null) {
//            for (Fragment f : manager.getFragments()) {
//                if (!fragment.equals(f)) {
//                    ft2.hide(f);
//                }
//            }
//        }
//        ft2.commit();
    }
}

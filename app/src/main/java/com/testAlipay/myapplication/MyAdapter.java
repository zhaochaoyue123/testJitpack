package com.testAlipay.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context ctx;
    /**
     * 所有部门的列表
     */
    private List<DepartmentTreeEntity> companyList;
    /**
     * 当前展示的部门列表（未处于收起状态）
     */
    private List<DepartmentTreeEntity> customerShownList;
    /**
     * 当前选中的部门
     */
    private DepartmentTreeEntity currentChosenDepartment;

    /**
     * 处于收缩状态的部门id
     * （由用户点击后触发）
     */
    private List<String> shrinkCustomerIdList;


    public MyAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setData(List<DepartmentTreeEntity> departmentList) {
        this.companyList = departmentList;
        customerShownList = new ArrayList<>(departmentList.size());
        shrinkCustomerIdList = new ArrayList<>();
        //默认选中第一项
        if (departmentList != null && departmentList.size() > 0) {
            currentChosenDepartment = departmentList.get(0);
        }
        initDepartmentShownList();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BaseViewHolder(LayoutInflater.from(ctx).inflate(R.layout.logistics_department_list_item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        final DepartmentTreeEntity departmentTreeEntity = customerShownList.get(position);

        LinearLayout llItemMonitorCompany = baseViewHolder.getView(R.id.ll_item_monitor_company);
        TextView tvCompanyName = baseViewHolder.getView(R.id.tv_company_name);
        tvCompanyName.setText(departmentTreeEntity.getTitle());
        ImageView ivSpreadLevel0 = baseViewHolder.getView(R.id.iv_spread_level_0);
        //选中项修改底色
        if (currentChosenDepartment != null && departmentTreeEntity.equals(currentChosenDepartment)) {
            tvCompanyName.setTextColor(Color.rgb(0x22, 0x88, 0xff));
        } else {
            tvCompanyName.setTextColor(Color.BLACK);
        }
        int level = departmentTreeEntity.getListLevel();
        if (departmentTreeEntity.getChildren() != null && departmentTreeEntity.getChildren().size() > 0) {
            //有子客户，显示展开/收缩 的图标
            ivSpreadLevel0.setVisibility(View.VISIBLE);
            if (shrinkCustomerIdList.contains(departmentTreeEntity.getId())) {
                //处于收缩状态，显示收缩对应的图标
                ivSpreadLevel0.setImageResource(R.mipmap.logistics_bt_arrow_down_gray);
            } else {
                //处于展开状态，显示展开对应的图标
                ivSpreadLevel0.setImageResource(R.mipmap.logistics_bt_arrow_up_gray);
            }
        } else {
            //没有子客户，不显示展开/收缩 的图标
            ivSpreadLevel0.setVisibility(View.INVISIBLE);
        }

        //根据客户的层级数值，设置margin。层级越高左边margin数值越大，
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivSpreadLevel0.getLayoutParams();
        params.setMargins(dip2px(ctx, 15 * (level + 1) - 8), 0, 0, 0);
        ivSpreadLevel0.setLayoutParams(params);

        //展开/收缩图标的点击事件，点击后收缩变为展开，展开变为收缩
        ivSpreadLevel0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shrinkCustomerIdList.contains(departmentTreeEntity.getId())) {
                    shrinkCustomerIdList.remove(departmentTreeEntity.getId());
                } else {
                    shrinkCustomerIdList.add(departmentTreeEntity.getId());
                }
                initDepartmentShownList();
                notifyDataSetChanged();
            }
        });


        //项点击事件，点击后该项即为选中项（需变色），并且触发回调
        llItemMonitorCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置当前点击项为选中项
                currentChosenDepartment = departmentTreeEntity;

                //回调
                if (listener != null) {
                    listener.onItemClick(departmentTreeEntity);
                }
                //更新界面
                notifyDataSetChanged();
            }
        });

    }

    /**
     * 初始化待显示的客户列表
     */
    public void initDepartmentShownList() {
        int shrinkLevel = 1000;
        customerShownList.clear();
        int i = 0;
        for (; i < companyList.size(); i++) {
            DepartmentTreeEntity customer = companyList.get(i);
            if (shrinkCustomerIdList.contains(customer.getId())) {
                //处于收缩状态的项，该项显示，
                customerShownList.add(customer);
                shrinkLevel = customer.getListLevel();
                //从下一个开始循环
                i++;
                for (; i < companyList.size(); i++) {
                    if (companyList.get(i).getListLevel() > shrinkLevel) {
                        //下级菜单，全部隐藏
                        continue;
                    } else {
                        //同级或上级,跳出循环，并将下标前移一位，让该对象进入上层的for循环判断，并初始化隐藏层级
                        i--;
                        shrinkLevel = 1000;
                        break;
                    }
                }
            } else {
                customerShownList.add(customer);
            }
        }
    }


    @Override
    public int getItemCount() {
        return customerShownList == null ? 0 : customerShownList.size();
    }


    /**
     * 项点击的回调监听
     */
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DepartmentTreeEntity company);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public DepartmentTreeEntity getCurrentDepartment() {
        return currentChosenDepartment;
    }

}

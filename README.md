
### در هنگام توسعه اپلیکیشن حتما رعایت شود


1.  در انتخاب نام متغیرها و توابع دقت زیادی شود. نامها باید کامل و جامع باشند و دقیق توضیح دهند برای چه منظوری استفاده می شوند.
2.  قبل از هر merge request باید نفر دوم کدهای مربوط به branch را به دقت بخواند و refactor انجام شود و سپس merge انجام شود.
3.  هر قسمت از اپلیکیشن که hard code انجام میشود و یا بنا به ضرورت نیاز به اصلاح دارد باید با TODO: مشخص شود

### نکاتی در رابطه با Git

**فرمت ایجاد branch ها در گیت:**

issue number + dash + work is going to do separated with underscores

12-fix_user_info_bugs

**فرمت پیامهای commit در گیت**


*  Mention issue number, date, previous stable release in each commit message.

*  List the tasks accomplished under each other with a bullet at the beginning of each line.



### نامگذاری کلاسها

در نامگذاری کلاسها دقت شود که نام کلاس با کاری که انجام میدهد همخوانی داشته باشد. 

**کلاسها حتما با توجه به کاری که انجام میدهند نامشان به پایان میرسد**
*  activity -> ClassNameActivity
*  fragment -> ClassNameFragment
*  adapter -> ClassNameAdapter
*  repository -> ClassNameRepository
*  entity -> ClassNameEntity
*  dao -> ClassNameDao
*  view model -> ClassNameViewModel

در لایه دامین نام کلاس حتما با Request و یا Get شروع می شود. اگر درخواست اطلاعات از سمت سرور باشد با Get و اگر فرستادن و یا ادیت اطلاعات به سمت سرور باشد با Request شروع می شود.

### نامگذاری layout ها

در نامگذاری layoutها دقت شود که نام کلاس با کاری که انجام میدهد همخوانی داشته باشد. 


**در نامگذاری layout ها باید توجه داشت که نام layout حتما با توجه به نقشی که دارد باید شروع شود**
*  activity -> ativity_class_name
*  fragment -> fragment_class_name
*  adapter -> item_class_name
*  included layout -> layout_name
*  custom view -> custom_view_name
*  dialog -> dialog_name
*  toolbar -> toolbar_name




### Class layout

Generally, the contents of a class is sorted in the following order:


*  Property declarations and initializer blocks

*  Secondary constructors

*  Method declarations

*  Companion object

Do not sort the method declarations alphabetically or by visibility, and do not separate regular methods from extension methods. Instead, put related stuff together, so that someone reading the class from top to bottom would be able to follow the logic of what's happening. Choose an order (either higher-level stuff first, or vice versa) and stick to it.

Put nested classes next to the code that uses those classes. If the classes are intended to be used externally and aren't referenced inside the class, put them in the end, after the companion object.


### ترتیب فیلدهای تعریف شده در فرگمنتها

1.  binding variable
2.  viewModel
3.  adapters and other autoCleared Values
4.  all other variables except for views
5.  variables related to view


### نحوه نامگذاری در فایل navigation


*  **fragment id**: exactly name of the fragment class but camel case

*  **action**: starts with action, then origin fragment, at last the destination fragment -> action_homeFragment_to_fundDetailFragment

*  **label**: name of the layout related to the fragment!

*  **argument**: name of the fragment + name of the variable -> fundDetailFragment + fundView -> fundDetailFundView


### نحوه نامگذاری در id در فایلهای layout



**id** = the abbreviation for the view + relate the view to the fragment(to be unique in application) + action done by the view:   
btn_confirm_change_password, cl_login_top_section, til_login_username

Abbreviation examples for most-used views:

*  Button -> btn
*  TextView -> tv
*  ImageView -> iv
*  RecyclerView -> rv
*  ScrollView -> sv
*  LinearLayout -> ll
*  ConstraintLayout -> cl
*  ProgressBar -> pb
*  Spinner -> sp
*  TextInputLayout -> til
*  EditText -> et
*  CardView -> cv
*  RelativeLayout -> rl
*  FrameLayout -> fl
*  RadioGroup -> rg
*  RadioButton -> rb

نکته ۱: از به کار بردن id های عام که ممکن است چندین بار در اپلیکیشن استفاده شود اجتناب شود. مثال: tv_message, btn_confirm و ....

نکته ۲: نیازی به آوردن کل نام فایل لایوت در id نیست. فقط باید بنحوی مشخص شود مربوط به این فرگمنت است.


### نحوه نامگذاری در strings

اگر در nav drawer, bottom nav , ... استفاده می شود این رشته ها باید حتما رشته مخصوص به خود را داشته باشند.

سایر رشته ها با یکی از این پیشوندها شروع شوند:
*  msg: اگر به صورت پیام به کاربر نشان داده میشود
*  label: اگر در دکمه، یا تکستویو و یا ... نشان داده میشود
*  error: اگر مربوط به ارور باشد
*  key: مربوط به sherdPreferences و ...
*  other: مربوط به سایر رشته ها 
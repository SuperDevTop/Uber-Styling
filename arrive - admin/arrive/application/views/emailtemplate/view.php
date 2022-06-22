
<?php
//echo'<pre>';
//print_r($user);
?>
<?php
//$userClass = $this->common->find('membership', $select = 'membership_name', $where = array('id' => $user['membership_id']), $resultType = 'array', $orderby = array());
//echo'<pre>';
//print_r($userClass);
//die('die');
//foreach ($category as $key => $value) {
//    $movieCat = $value->cat_name;
//}
//$genresName = array();
//foreach ($genres as $key => $value) {
//    $genresName[] = $value->genres_name;
//}
//$genres = implode(', ', $genresName);
//
//$tagName = array();
//foreach ($tag as $key => $value) {
//    $tagName[] = $value->tag_name;
//}
//$tags = implode(', ', $tagName);
?>


<div class="page-content">
    <div class="container-fluid">
        <section class="card">
            <div class="card-block">
                <table id="example" class="display table table-bordered" cellspacing="0" width="100%">

                    <tbody>
                        <tr>
                            <th>Template Name</th>
                            <td><?php echo ucfirst($en_email_template['template_name']); ?></td>
                        </tr>
                        <tr>
                            <th>Template Subject</th>
                            <td><?php echo $en_email_template['template_subject']; ?></td>
                        </tr>
                        <tr>
                            <th>Template Description</th>
                            <td><?php echo $en_email_template['template_description']; ?></td>
                        </tr>
                        <tr>
                            <th>Template Status</th>

                            <td><?php
                                if ($en_email_template['status'] == 1) {
                                    echo 'Active';
                                } else {
                                    echo 'Deactive';
                                }
                                ?></td>

                        </tr>

                    </tbody>
                </table>
            </div>
        </section>
    </div><!--.container-fluid-->
</div><!--.page-content-->


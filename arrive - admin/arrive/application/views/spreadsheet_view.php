<?php
// We change the headers of the page so that the browser will know what sort of file is dealing with. Also, we will tell the browser it has to treat the file as an attachment which cannot be cached.

header("Content-type: application/octet-stream");
header("Content-Disposition: attachment; filename=exceldata.xls");
header("Pragma: no-cache");
header("Expires: 0");
?>

<table border='1'>


    <tr>
        <td>Sr. No.</td>
        <td>Task Name</td>
        <td>Poster Name</td>
        <td>Worker Name</td>
        <td>Schedule Date</td>
    </tr>
    <?php
    $i = 1;
    foreach ($allRecord as $key => $value) {
        ?>
        <tr>

            <td class=" "><?php echo $i; ?></td>
            <td class=" "><?php echo $value['title'] ?></td>
            <td class=" "><?php echo $value['posterName'] ?></td>
            <td class=" "><?php echo $value['workerName'] ?></td>
            <td class=" "><?php echo $value['due_date'] ?></td>

        </tr>
        <br style="mso-data-placement:same-cell;" />
        <?php
        $i++;
    }
    ?>




</table>
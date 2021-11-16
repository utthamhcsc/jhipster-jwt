import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDepartment } from '../department.model';
import { DepartmentService } from '../service/department.service';
import { DepartmentDeleteDialogComponent } from '../delete/department-delete-dialog.component';

@Component({
  selector: 'jhi-department',
  templateUrl: './department.component.html',
})
export class DepartmentComponent implements OnInit {
  departments?: IDepartment[];
  isLoading = false;
  currentSearch: string;

  constructor(protected departmentService: DepartmentService, protected modalService: NgbModal, protected activatedRoute: ActivatedRoute) {
    this.currentSearch = this.activatedRoute.snapshot.queryParams['search'] ?? '';
  }

  loadAll(): void {
    this.isLoading = true;
    if (this.currentSearch) {
      this.departmentService
        .search({
          query: this.currentSearch,
        })
        .subscribe(
          (res: HttpResponse<IDepartment[]>) => {
            this.isLoading = false;
            this.departments = res.body ?? [];
          },
          () => {
            this.isLoading = false;
          }
        );
      return;
    }

    this.departmentService.query().subscribe(
      (res: HttpResponse<IDepartment[]>) => {
        this.isLoading = false;
        this.departments = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDepartment): number {
    return item.id!;
  }

  delete(department: IDepartment): void {
    const modalRef = this.modalService.open(DepartmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.department = department;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

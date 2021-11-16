jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskService } from '../service/task.service';

import { TaskComponent } from './task.component';

describe('Task Management Component', () => {
  let comp: TaskComponent;
  let fixture: ComponentFixture<TaskComponent>;
  let service: TaskService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TaskComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { queryParams: {} } },
        },
      ],
    })
      .overrideTemplate(TaskComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TaskComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TaskService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.tasks?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
